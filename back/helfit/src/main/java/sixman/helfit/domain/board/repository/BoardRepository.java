package sixman.helfit.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.board.entity.Board;


import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT b FROM Board b WHERE b.category.categoryId = :categoryId AND b.boardId =:boardId")
    Optional<Board> findBoardByIds(@Param("categoryId") Long categoryId, @Param("boardId") Long boardId);

    @Query(value = "SELECT b FROM Board b WHERE b.category.categoryId = :categoryId ORDER BY b.createdAt DESC")
    List<Board> findTop3ByCategoryIdOrderByCreatedAtDesc(@Param("categoryId") Long categoryId, Pageable pageable);
    //^태형 만듬

    @Query(value = "SELECT b FROM Board b WHERE b.category.categoryId = :categoryId")
    Page<Board> findBoardByCategoryId(@Param("categoryId") Long categoryId, PageRequest pageable);

    @Query(value = "SELECT b FROM Board b WHERE b.boardId = :boardId")
    Optional<Board> findBoardByBoardId(@Param("boardId") Long boardId);
}
