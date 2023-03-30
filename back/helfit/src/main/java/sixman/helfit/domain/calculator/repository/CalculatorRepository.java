package sixman.helfit.domain.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface CalculatorRepository extends JpaRepository<Calculator, Long> {
    Optional<Calculator> findByCalculatorId(Long CalculatorId);
    //@Query("SELECT c FROM Calculator c WHERE c.user.userId = :userId ORDER BY c.createdAt DESC")
    @Query(nativeQuery = true, value = "SELECT * FROM calculator c WHERE c.user_id = :userId ORDER BY c.created_at DESC LIMIT 1")
    Optional<Calculator> findFirstByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    @Query("SELECT c FROM Calculator c WHERE c.user.userId = :userId ORDER BY c.modifiedAt DESC")
    List<Calculator> findAllByUserIdOrderByModifiedAtDesc(Long userId);
}
