package sixman.helfit.domain.user.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.entity.UserRefreshToken;
import sixman.helfit.domain.user.mapper.UserMapper;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;
import sixman.helfit.utils.CookieUtil;
import sixman.helfit.utils.HeaderUtil;
import sixman.helfit.utils.UriUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private static final String DEFAULT_URI = "/api/v1/users";

    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    private final static long THREE_DAYS_MSE = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto.Signup requestBody) {
        User user = userService.createUser(userMapper.userDtoSignupToUser(requestBody));

        URI uri = UriUtil.createUri(DEFAULT_URI, user.getUserId());

        return ResponseEntity.created(uri).body(ApiResponse.ok("message", "회원등록이 완료됐습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody UserDto.Login requestBody) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                requestBody.getId(),
                requestBody.getPassword()
            )
        );

        String id = requestBody.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);
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

    @GetMapping("{user-id}")
    public ResponseEntity<?> getUser(@Positive @PathVariable("user-id") Long userId) {
        User user = userService.findVerifiedUserByUserId(userId);

        return ResponseEntity.ok().body(ApiResponse.ok("data", user));
    }

    @GetMapping("/refresh")
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

    @PatchMapping("{user-id}")
    public ResponseEntity<?> patchUser(
        @Positive @PathVariable("user-id") Long userId,
        @Valid @RequestBody UserDto.Patch requestBody
    ) {
        User user = userService.updateUser(userId, userMapper.userDtoPatchToUser(requestBody));

        UserDto.Response response = userMapper.userToUserDtoResponse(user);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }
}
