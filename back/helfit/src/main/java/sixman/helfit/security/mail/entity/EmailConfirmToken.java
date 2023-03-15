package sixman.helfit.security.mail.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS_EMAIL_CONFIRM_TOKEN")
public class EmailConfirmToken {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String tokenId;

    @Column
    private Long userId;

    @Column
    private Boolean expired;
}
