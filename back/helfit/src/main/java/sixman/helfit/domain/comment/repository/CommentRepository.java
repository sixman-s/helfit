package sixman.helfit.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.comment.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value ="SELECT c FROM COMMENTS c WHERE c.board.boardId = :boardId")
    List<Comment> findComments(@Param("boardId") Long boardId);

}
