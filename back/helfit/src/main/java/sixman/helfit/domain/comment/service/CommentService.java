package sixman.helfit.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.service.BoardService;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.comment.repository.CommentRepository;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.entity.UserPrincipal;

import java.util.List;
import java.util.Optional;

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

    public Comment updateComment(Comment comment, long userId, long boardId,long commentId) {
        Comment findComment = findVerifiedComment(userId,boardId,commentId);

        Optional.ofNullable(comment.getCommentBody())
                .ifPresent(findComment::setCommentBody);

        return commentRepository.save(findComment);
    }
    @Transactional(readOnly = true)
    public List<Comment> getComments(long boardId){
        return commentRepository.findComments(boardId);
    }

    public void deleteComment(long userId,long boardId,long commentId) {
        Comment comment = findVerifiedComment(userId,boardId,commentId);

        commentRepository.delete(comment);

    }
    @Transactional(readOnly = true)
    public Comment findVerifiedComment (long userId,long boardId,long commentId){
        Optional<Comment> optionalComment = commentRepository.findComment(boardId,userId,commentId);
        Comment findComment = optionalComment.orElseThrow(() ->new BusinessLogicException(ExceptionCode.COMMENTS_NOT_FOUND));
        if(findComment.getUser().getUserId() != userId){
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);
        }
        return findComment;
    }

    public List<Comment> findCommentsByUserId(UserPrincipal userPrincipal,int page){
        Page<Comment> pageComment = commentRepository.findCommentsByUserId(userPrincipal.getUser().getUserId(), PageRequest.of(page,5,
                Sort.by("commentId").descending()));
        return pageComment.getContent();
    }
    public Integer getCommentsCountByUserId(UserPrincipal userPrincipal){
        return commentRepository.getCommentCountByUserId(userPrincipal.getUser().getUserId());
    }

}
