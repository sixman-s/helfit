package sixman.helfit.domain.comment.service;

import org.springframework.stereotype.Service;
import sixman.helfit.domain.board.service.BoardService;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.comment.repository.CommentRepository;
import sixman.helfit.domain.user.service.UserService;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BoardService boardService;

    public CommentService(CommentRepository commentRepository, UserService userService, BoardService boardService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.boardService = boardService;
    }

    public Comment createComment(Comment comment,long userId,long boardId){
        comment.setUser(userService.findUserByUserId(userId));
        comment.setBoard(boardService.findBoardById(boardId));
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(long boardId){
        return commentRepository.findComments(boardId);
    }

}
