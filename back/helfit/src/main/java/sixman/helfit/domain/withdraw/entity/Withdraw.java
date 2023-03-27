package sixman.helfit.domain.withdraw.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.user.converter.CryptoConverter;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.security.entity.ProviderType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Withdraw extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long withdrawId;

    private String id;

    @Convert(converter = CryptoConverter.class)
    private String email;

    @Convert(converter = CryptoConverter.class)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    // # Ex: User 단방향 연관관계 Entity 개별 Delete CASCADE
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "USER_ID")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // private User user;

    @Builder
    public Withdraw(String id, String email, String nickname, ProviderType providerType) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.providerType = providerType;
    }
}
