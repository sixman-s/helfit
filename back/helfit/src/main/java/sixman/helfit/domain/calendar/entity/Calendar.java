package sixman.helfit.domain.calendar.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Calendar extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer kcal;

    private String recodedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
