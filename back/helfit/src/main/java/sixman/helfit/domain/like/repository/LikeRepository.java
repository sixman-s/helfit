package sixman.helfit.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.like.entity.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "SELECT l FROM Like l WHERE l.user.userId = :userId AND l.board.boardId = :boardId")
    Optional<Like> findByIds(@Param("userId") Long userId,@Param("boardId") Long boardId);
}
