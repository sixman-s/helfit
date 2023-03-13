package sixman.helfit.domain.comment.service;

import org.springframework.stereotype.Service;
import sixman.helfit.domain.comment.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


}
