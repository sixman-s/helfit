package sixman.helfit.domain.board.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.board.entity.Board;


import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT b FROM BOARDS b WHERE b.category.categoryId = :categoryId AND b.user.userId = :userId AND b.boardId =:boardId")
    Optional<Board> findBoardByIds(@Param("categoryId") Long categoryId,@Param("userId") Long userId,@Param("boardId") Long boardId);

    @Query(value = "SELECT b FROM BOARDS b WHERE b.category.id = :categoryId ORDER BY b.createdAt DESC")
    List<Board> findTop3ByCategoryIdOrderByCreatedAtDesc(@Param("categoryId") Long categoryId, Pageable pageable);
    //^태형 만듬
}
