package sixman.helfit.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(length = 64, nullable = false, unique = true)
    private String userId;

    @JsonIgnore
    @Column(length = 128, nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType roleType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @NotNull
    @Column(length = 512)
    private String imageUrl;

    public User(String userId, String name, String email, String emailVerifiedYn, String imageUrl, ProviderType providerType, RoleType roleType) {
        this.userId = userId;
        this.name = name;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.imageUrl = imageUrl != null ? imageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}
