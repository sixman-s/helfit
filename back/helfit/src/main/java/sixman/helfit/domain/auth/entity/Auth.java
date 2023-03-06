package sixman.helfit.domain.auth.entity;

import lombok.Getter;

@Getter
public class Auth {
    private String accessToken;
    private String refreshToken;
}
