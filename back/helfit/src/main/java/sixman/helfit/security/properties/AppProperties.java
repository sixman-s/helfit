package sixman.helfit.security.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiry;
        private long refreshTokenExpiry;
    }

    public static final class OAuth2 {
        private List<String> authorizedSuccessRedirectUris = new ArrayList<>();
        private List<String> authorizedFailRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedSuccessRedirectUris() {
            return authorizedSuccessRedirectUris;
        }

        public OAuth2 authorizedSuccessRedirectUri(List<String> authorizedSuccessRedirectUri) {
            this.authorizedSuccessRedirectUris = authorizedSuccessRedirectUri;
            return this;
        }

        public List<String> getAuthorizedFailRedirectUris() {
            return authorizedFailRedirectUris;
        }

        public OAuth2 authorizedFailRedirectUri(List<String> authorizedFailRedirectUri) {
            this.authorizedFailRedirectUris = authorizedFailRedirectUri;
            return this;
        }
    }
}
