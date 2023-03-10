package sixman.helfit.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.info.OAuth2UserInfo;
import sixman.helfit.security.info.OAuth2UserInfoFactory;
import sixman.helfit.security.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;
import sixman.helfit.domain.user.entity.UserRefreshToken;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.utils.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static sixman.helfit.security.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.*;

@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${front.domain}")
    private String frontDomain;

    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                                           .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException(
                "승인되지 않은 리디렉션 'URI' 가 있어 인증을 진행할 수 없습니다."
            );
        }

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = (OidcUser) authentication.getPrincipal();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();
        RoleType roleType = hasAuthority(authorities, RoleType.ADMIN.getCode()) ? RoleType.ADMIN : RoleType.USER;

        Date now = new Date();

        // # Access Token
        AuthToken accessToken = authTokenProvider.createAuthToken(
            userInfo.getId(),
            roleType.getCode(),
            new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // # Refresh Token
        AuthToken refreshToken = authTokenProvider.createAuthToken(
            appProperties.getAuth().getTokenSecret(),
            new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        // # DB Access
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findById(userInfo.getId());

        if (userRefreshToken != null) {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        } else {
            userRefreshToken = new UserRefreshToken(userInfo.getId(), refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return createURI(accessToken, refreshToken).toString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        if (authorities == null) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                   .stream()
                   .anyMatch(authorizedRedirectUri -> {
                       // Only validate host and port. Let the clients use different paths if they want to
                       URI authorizedURI = URI.create(authorizedRedirectUri);

                       return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) &&
                                  authorizedURI.getPort() == clientRedirectUri.getPort();
                   });
    }

    private URI createURI(AuthToken accessToken, AuthToken refreshToken) {
        List<String> authorizedRedirectUris = appProperties.getOauth2().getAuthorizedRedirectUris();
        String callback = authorizedRedirectUris.stream().filter(d -> d.contains(frontDomain)).findAny()
                       .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCESS_DENIED));

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken.getToken());
        // ! Cookie 정보 전달로 대체함
        // queryParams.add("refresh_token", refreshToken.getToken());

        return UriComponentsBuilder.fromUriString(callback)
                      .queryParams(queryParams)
                      .build().toUri();
    }
}
