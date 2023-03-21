package sixman.helfit.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sixman.helfit.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value ="SELECT c FROM Comment c WHERE c.board.boardId = :boardId")
    List<Comment> findComments(@Param("boardId") Long boardId);
    @Query(value = "SELECT c FROM Comment c WHERE c.board.boardId = :boardId AND c.user.userId =:userId AND c.commentId =:commentId")
    Optional<Comment> findComment(@Param("boardId") Long boardId, @Param("userId") Long userId,
                                  @Param("commentId") Long commentId);

}
