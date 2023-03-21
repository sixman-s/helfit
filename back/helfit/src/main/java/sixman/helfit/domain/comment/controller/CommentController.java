package sixman.helfit.domain.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.comment.mapper.CommentMapper;
import sixman.helfit.domain.comment.service.CommentService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private static final String COMMENT_DEFAULT_URI = "/api/v1/comment";

    private final CommentService commentService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }
    @PostMapping("{user-id}/{board-id}")
    public ResponseEntity postComment(@Positive @PathVariable ("user-id") long userId,
                                      @Positive @PathVariable ("board-id") long boardId,
                                      @Valid @RequestBody CommentDto.PostAndPatch requestBody){
        Comment comment = mapper.commentPostToComment(requestBody);
        Comment savedComment = commentService.createComment(comment,userId,boardId);
        URI uri = UriUtil.createUri(COMMENT_DEFAULT_URI, savedComment.getCommentId());

        return ResponseEntity.created(uri).body(ApiResponse.created());
    }

    @GetMapping("{board-id}")
    public ResponseEntity getComment(@Positive @PathVariable ("board-id") long boardId){
        List<Comment> comments = commentService.getComments(boardId);
        return new ResponseEntity(mapper.commentsToResponseDtos(comments),HttpStatus.OK);
    }

    @PatchMapping("{user-id}/{board-id}/{comment-id}")
    public ResponseEntity patchComment(@Positive @PathVariable ("user-id") long userId,
                                      @Positive @PathVariable ("board-id") long boardId,
                                       @Positive @PathVariable ("comment-id") long commentId,
                                      @Valid @RequestBody CommentDto.PostAndPatch requestBody) {
        Comment comment = commentService.updateComment(mapper.commentPostToComment(requestBody),userId,boardId,commentId);

        return new ResponseEntity(mapper.commentToResponseDto(comment),HttpStatus.OK);
    }

    @DeleteMapping("{user-id}/{board-id}/{comment-id}")
    public ResponseEntity deleteComment(@Positive @PathVariable ("user-id") long userId,
                                        @Positive @PathVariable ("board-id") long boardId,
                                        @Positive @PathVariable ("comment-id") long commentId){
        commentService.deleteComment(userId,boardId,commentId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
