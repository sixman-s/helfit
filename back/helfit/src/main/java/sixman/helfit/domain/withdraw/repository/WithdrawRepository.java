package sixman.helfit.domain.withdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.withdraw.entity.Withdraw;

import java.util.Optional;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
    Optional<Withdraw> findUserById(String id);
}
