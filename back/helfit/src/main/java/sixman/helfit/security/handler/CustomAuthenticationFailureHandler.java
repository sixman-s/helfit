package sixman.helfit.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.response.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * # UserController::login -> UsernamePasswordAuthenticationFilter 구현시 사용
 * ! 필터 핸들러 필요시 로그인 프로세스 절차 변경
 */
// @Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.error("자격증명에 실패했습니다 = {}", exception.getMessage());

        ErrorResponse.sendErrorResponse(response, ExceptionCode.UNAUTHORIZED);
    }
}
