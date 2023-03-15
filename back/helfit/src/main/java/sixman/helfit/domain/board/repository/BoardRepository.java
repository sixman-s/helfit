package sixman.helfit.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.board.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT b FROM BOARDS b WHERE b.category.categoryId = :categoryId AND b.user.userId = :userId AND b.boardId =:boardId")
    Optional<Board> findBoardByIds(@Param("categoryId") Long categoryId,@Param("userId") Long userId,@Param("boardId") Long boardId);
}
