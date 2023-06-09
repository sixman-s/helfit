package sixman.helfit.domain.calculator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.audit.Auditable;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.calculator.helper.CalculatorHelper;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Calculator extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calculatorId;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    @Enumerated(EnumType.STRING)
    private ActivityLevel ActivityLevel;
    @Column
    private Double result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHYSICAL")
    private Physical physical;


    public static void checkNotFoundUser(User user) {
        if (user == null) {
            throw new BusinessLogicException(ExceptionCode.CALCULATOR_NOT_FOUND);
        }
    }
}
