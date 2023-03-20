package sixman.helfit.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.exception.TokenValidFailedException;
import sixman.helfit.response.ErrorResponse;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;
import sixman.helfit.utils.HeaderUtil;
import sixman.helfit.security.service.CustomUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider authTokenProvider;
    private final CustomUserDetailService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = authTokenProvider.convertAuthToken(tokenStr);

        // Global Advice 진입전 예외 처리
        try {
            if (token.validate()) {
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (UsernameNotFoundException e) {
            ErrorResponse.sendErrorResponse(response, ExceptionCode.USER_NOT_FOUND);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Authentication getAuthentication(AuthToken authToken) {
        if (!authToken.validate()) throw new TokenValidFailedException();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authToken.getTokenClaims().getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, authToken, userDetails.getAuthorities());
    }
}
