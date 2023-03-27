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
    Page<Board> findBoardsByCategoryId(@Param("categoryId") Long categoryId, PageRequest pageable);

    @Query(value = "SELECT b FROM Board b WHERE b.user.userId = :userId")
    Page<Board> findBoardsByUserId(@Param("userId") Long userId,Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM Board b WHERE b.user.userId = :userId")
    Integer getCountByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT COUNT(*) FROM Board b WHERE b.category.categoryId = :categoryId")
    Integer countByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT b FROM Board b WHERE b.category.categoryId = :categoryId ORDER BY b.boardPoint DESC")
    List<Board> findTop4Boards(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b WHERE b.user.nickname = :userNickname")
    Page<Board> findByUserNickname(@Param("userNickname") String userNickname, PageRequest pageable);

    @Query(value = "SELECT COUNT(*) FROM Board b WHERE b.user.nickname = :userNickname")
    Integer getCountByNickname(@Param("userNickname") String userNickname);

    @Query(value ="SELECT b FROM Board b WHERE b.title LIKE %:title%")
    Page<Board> findByTitle(@Param("title") String title, PageRequest pageable);

    @Query(value ="SELECT COUNT(*) FROM Board b WHERE b.title LIKE %:title%")
    Integer getCountByTitle(@Param("title") String title);

    @Query(value ="SELECT b FROM Board b WHERE b.text LIKE %:text%")
    Page<Board> findByText(@Param("text") String text, PageRequest pageable);

    @Query(value ="SELECT COUNT(*) FROM Board b WHERE b.text LIKE %:text%")
    Integer getCountByText(@Param("text") String text);

}
