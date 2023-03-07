package sixman.helfit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.user.entity.UserRefreshToken;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findById(String id);
    UserRefreshToken findByIdAndRefreshToken(String id, String refreshToken);
}
