package sixman.helfit.security.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sixman.helfit.security.token.AuthTokenProvider;

@Configuration
public class JwtConfig {
    @Value("${app.auth.tokenSecret}")
    private String secret;

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(secret);
    }
}
