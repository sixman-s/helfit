package sixman.helfit.domain.statistics.entity;

import lombok.Getter;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Stat extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CALENDAR_ID")
    private Calendar calendar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CALCULATOR_ID")
    private Calculator calculator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    private Integer kcal;
    private String recodedAt;
    private String boardImageUrl;
    private String title;
    private String text;
    private Integer weight;
    private String lastModifiedAt;
}
