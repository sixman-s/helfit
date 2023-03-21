package sixman.helfit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static sixman.helfit.domain.user.entity.User.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);
    List<User> findByModifiedAtAndUserStatusEquals(LocalDateTime localDateTime, UserStatus status);
}
