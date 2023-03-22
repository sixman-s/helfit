package sixman.helfit.domain.user.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.entity.UserRefreshToken;
import sixman.helfit.domain.user.mapper.UserMapper;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.global.annotations.CurrentUser;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.mail.entity.EmailConfirmToken;
import sixman.helfit.security.mail.service.EmailConfirmTokenService;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;
import sixman.helfit.utils.CookieUtil;
import sixman.helfit.utils.HeaderUtil;
import sixman.helfit.utils.UriUtil;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;

import static sixman.helfit.domain.user.entity.User.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    @Value("${domain.front}")
    private String frontDomain;

    private static final String DEFAULT_URI = "/api/v1/users";

    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final FileService fileService;
    private final EmailConfirmTokenService emailConfirmTokenService;

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSE = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    /*
     * # Local 회원 가입
     *
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto.Signup requestBody) throws MessagingException {
        User user = userService.createUser(userMapper.userDtoSignupToUser(requestBody));

        EmailConfirmToken emailConfirmToken = emailConfirmTokenService.createEmailConfirmToken(user.getUserId());
        emailConfirmTokenService.sendEmail(user.getEmail(), emailConfirmToken.getTokenId());

        URI uri = UriUtil.createUri(DEFAULT_URI, user.getUserId());

        return ResponseEntity.created(uri).body(ApiResponse.created());
    }

    /*
     * # Local 회원 로그인
     *
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
        HttpServletRequest request,
        HttpServletResponse response,
        @Valid @RequestBody UserDto.Login requestBody
    ) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                requestBody.getId(),
                requestBody.getPassword()
            )
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        // ! 탈퇴 회원 로그인 시도
        if (user.getUserStatus().equals(UserStatus.USER_QUIT))
            throw new BusinessLogicException(ExceptionCode.USER_WITHDRAW);

        // ! 이메일 인증 프로세스 예외처리 미적용 (RDS 연동시 주석 제거)
        if (user.getEmailVerifiedYn().equals(User.EmailVerified.N))
            throw new BusinessLogicException(ExceptionCode.EMAIL_NOT_CONFIRMED);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String id = user.getId();
        Date now = new Date();

        // # Access Token 생성
        AuthToken accessToken = authTokenProvider.createAuthToken(
            id,
            ((UserPrincipal) authentication.getPrincipal()).getUser().getRoleType().getCode(),
            new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // # Refresh Token 생성
        AuthToken refreshToken = authTokenProvider.createAuthToken(
            appProperties.getAuth().getTokenSecret(),
            new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        // # DB - Refresh Token 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findById(id);
        if (userRefreshToken == null) {
            userRefreshToken = new UserRefreshToken(id, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ResponseEntity.ok().body(ApiResponse.ok("accessToken", accessToken.getToken()));
    }

    /*
     * # 사용자 refresh-token 재발급
     *
     */
    @GetMapping("/refresh-token")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // # Access Token
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authAccessToken = authTokenProvider.convertAuthToken(accessToken);

        // # Access Token 검증
        if (!authAccessToken.validate()) throw new BusinessLogicException(ExceptionCode.INVALID_ACCESS_TOKEN);

        // # Access Token expired 확인
        Claims claims = authAccessToken.getExpiredTokenClaims();
        if (claims == null) throw new BusinessLogicException(ExceptionCode.NOT_EXPIRED_TOKEN_YET);

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // # Refresh Token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN).map(Cookie::getValue).orElse(null);
        AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);

        // # Refresh Token 검증
        if (!authRefreshToken.validate()) throw new BusinessLogicException(ExceptionCode.INVALID_REFRESH_TOKEN);

        // # DB - Refresh token 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) throw new BusinessLogicException(ExceptionCode.INVALID_REFRESH_TOKEN);

        Date now = new Date();

        // # Access Token 신규 생성
        authAccessToken = authTokenProvider.createAuthToken(
            userId,
            roleType.getCode(),
            new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // # Refresh token expiry 3일 이하 일시 갱신 처리
        if (validTime <= THREE_DAYS_MSE) {
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = authTokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
            );

            // # DB refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ResponseEntity.ok().body(ApiResponse.ok("access_token", authAccessToken.getToken()));
    }

    /*
     * # 사용자 이메일 인증
     *
     */
    @GetMapping("/confirm-email")
    public ModelAndView confirmEmail(@RequestParam("token-id") String tokenId) {
        EmailConfirmToken verifiedConfirmToken =
            emailConfirmTokenService.findVerifiedConfirmTokenByTokenId(tokenId);
        emailConfirmTokenService.updateEmailConfirmToken(verifiedConfirmToken.getTokenId());
        userService.updateUserEmailVerifiedYn(verifiedConfirmToken.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("email_confirm");
        modelAndView.addObject("link", frontDomain);

        return modelAndView;
    }

    /*
     * # 사용자 이메일 인증 재발송
     *
     */
    @GetMapping("/resend-confirm-email")
    public void resendConfirmEmail(@CurrentUser UserPrincipal userPrincipal) throws MessagingException {
        EmailConfirmToken verifiedConfirmToken =
            emailConfirmTokenService.findVerifiedConfirmTokenByUserId(userPrincipal.getUser().getUserId());

        emailConfirmTokenService.sendEmail(userPrincipal.getUser().getEmail(), verifiedConfirmToken.getTokenId());
    }

    /*
     * # 사용자 정보 조회
     *
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.findUserByUserId(userPrincipal.getUser().getUserId());

        return ResponseEntity.ok().body(ApiResponse.ok("data", user));
    }

    /*
     * # 사용자 정보 변경
     *
     */
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(
        @Valid @RequestBody UserDto.Update requestBody,
        @CurrentUser UserPrincipal userPrincipal
    ) {
        User updatedUser = userService.updateUser(userPrincipal.getUser().getUserId(), userMapper.userDtoPatchToUser(requestBody));

        UserDto.Response response = userMapper.userToUserDtoResponse(updatedUser);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    /*
     * # 사용자 비밀번호 변경
     *
     */
    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserPassword(
        @Valid @RequestBody UserDto.Password requestBody,
        @CurrentUser UserPrincipal userPrincipal
    ) {
        userService.updateUserPassword(
            userPrincipal.getUser().getUserId(),
            userMapper.userDtoPasswordToUser(requestBody)
        );

        return ResponseEntity.ok().body(ApiResponse.ok());
    }

    /*
     * # 회원 프로필 이미지 등록 & 수정
     *
     */
    @PostMapping("/profile-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserProfileImage(
        @RequestParam MultipartFile multipartFile,
        @CurrentUser UserPrincipal userPrincipal
    ) throws Exception {
        String imagePath = fileService.uploadFile(multipartFile);
        userService.updateUserProfileImage(userPrincipal.getUser().getUserId(), imagePath);

        return ResponseEntity.ok().body(ApiResponse.ok("resource", imagePath));
    }

    /*
     * # 회원 프로필 이미지 삭제
     *
     */
    @DeleteMapping("/profile-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserProfileImage(@CurrentUser UserPrincipal userPrincipal) {
        userService.updateUserProfileImage(userPrincipal.getUser().getUserId(), null);

        return ResponseEntity.ok().body(ApiResponse.noContent());
    }

    /*
     * # 회원 탈퇴 (Withdraw Table 데이터 이동)
     *q
     */
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdrawUser(
        @Valid @RequestBody UserDto.Password requestBody,
        @CurrentUser UserPrincipal userPrincipal
    ) {
        userService.withdrawUser(
            userPrincipal.getUser().getUserId(),
            userMapper.userDtoPasswordToUser(requestBody)
        );

        return ResponseEntity.noContent().build();
    }
}
