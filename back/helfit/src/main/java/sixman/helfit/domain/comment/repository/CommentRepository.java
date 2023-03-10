package sixman.helfit.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
