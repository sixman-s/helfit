package sixman.helfit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.user.entity.UserRefreshToken;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserId(String userId);
    UserRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
}
