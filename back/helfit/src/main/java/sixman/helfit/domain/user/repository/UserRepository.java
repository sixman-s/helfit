package sixman.helfit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);
}
