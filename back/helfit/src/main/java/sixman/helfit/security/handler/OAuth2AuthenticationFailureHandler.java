package sixman.helfit.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.utils.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Value("${domain.front}")
    private String frontDomain;

    private final AppProperties appProperties;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        // ! 리다이렉트 타켓 변경
        // String targetUrl = CookieUtil.getCookie(request, OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
        //                        .map(Cookie::getValue)
        //                        .orElse(("/"));
        exception.printStackTrace();

        String targetUrl = createURI(exception).toString();

        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private URI createURI(Exception exception) {
        List<String> authorizedFailRedirectUris = appProperties.getOauth2().getAuthorizedFailRedirectUris();
        String callback = authorizedFailRedirectUris.stream()
                              .filter(d -> d.contains(frontDomain))
                              .findAny()
                              .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCESS_DENIED));

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("messages", exception.getLocalizedMessage());

        return UriComponentsBuilder.fromUriString(callback)
                   .queryParams(queryParams)
                   .build()
                   .toUri();
    }
}
