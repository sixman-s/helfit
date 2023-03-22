package sixman.helfit.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
