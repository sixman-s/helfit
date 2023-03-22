package sixman.helfit.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.entity.UserRefreshToken;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;
import sixman.helfit.utils.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import static sixman.helfit.domain.user.dto.UserDto.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AppProperties appProperties;

    private final AuthenticationManager authenticationManager;
    private final AuthTokenProvider authTokenProvider;

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSE = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getId(), login.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
        User user = userPrincipal.getUser();

        // ! 탈퇴 회원 로그인 시도
        if (user.getUserStatus().equals(User.UserStatus.USER_QUIT))
            throw new BusinessLogicException(ExceptionCode.USER_WITHDRAW);

        // ! 이메일 인증 프로세스 예외처리 미적용 (RDS 연동시 주석 제거)
        // if (user.getEmailVerifiedYn().equals(User.EmailVerified.N))
        //     throw new BusinessLogicException(ExceptionCode.EMAIL_NOT_CONFIRMED);

        SecurityContextHolder.getContext().setAuthentication(authResult);

        String id = user.getId();
        Date now = new Date();

        // # Access Token 생성
        AuthToken accessToken = authTokenProvider.createAuthToken(
            id,
            user.getRoleType().getCode(),
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

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter()
            .write(new ObjectMapper()
                       .writeValueAsString(ApiResponse.ok("accessToken", accessToken.getToken()))
            );

        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
