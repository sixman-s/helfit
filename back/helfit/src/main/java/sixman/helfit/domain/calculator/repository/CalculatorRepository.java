package sixman.helfit.domain.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.user.entity.User;

public interface CalculatorRepository extends JpaRepository<Calculator, Long> {
    Calculator findByUser(User user);
}
