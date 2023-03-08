package sixman.helfit.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderType {
    GOOGLE,
    GITHUB,
    NAVER,
    KAKAO,
    LOCAL
    ;
}
