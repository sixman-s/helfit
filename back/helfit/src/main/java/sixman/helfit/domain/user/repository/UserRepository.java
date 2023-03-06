package sixman.helfit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
}
