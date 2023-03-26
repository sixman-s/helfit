package sixman.helfit.domain.physical.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Physical extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long physicalId;

    @Column(length = 8)
    private Integer birth;

    private Integer height;
    private Integer weight;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Physical(Integer birth, Integer height, Integer weight, Gender gender) {
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public enum Gender {
        MALE,
        FEMALE
        ;
    }
}
