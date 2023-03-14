package sixman.helfit.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sixman.helfit.response.ErrorResponse.sendErrorResponse;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider authTokenProvider;
    private final CustomUserDetailService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = authTokenProvider.convertAuthToken(tokenStr);

        try {
            if (token.validate()) {
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            ErrorResponse.sendErrorResponse(response, ExceptionCode.USERS_NOT_FOUND);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Authentication getAuthentication(AuthToken authToken) {
        if (authToken.validate()) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authToken.getTokenClaims().getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, authToken, userDetails.getAuthorities());
        } else {
            throw new TokenValidFailedException();
        }
    }

    // private void sendErrorResponse(HttpServletResponse response, ExceptionCode exceptionCode) {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //
    //     response.setStatus(exceptionCode.getStatus());
    //     response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    //     ErrorResponse errorResponse = ErrorResponse.of(exceptionCode);
    //
    //     try {
    //         response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // ! FilterChange 선순위 오류 수정 전 기록
    // private Authentication getAuthentication(AuthToken authToken) {
    //     if (authToken.validate()) {
    //         Claims claims = authToken.getTokenClaims();
    //
    //         Collection<? extends GrantedAuthority> authorities =
    //             Arrays.stream(new String[]{claims.get("role").toString()})
    //                 .map(SimpleGrantedAuthority::new)
    //                 .collect(Collectors.toList());
    //
    //         User principal = new User(claims.getSubject(), "", authorities);
    //
    //         return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
    //     } else {
    //         throw new TokenValidFailedException();
    //     }
    // }
}
