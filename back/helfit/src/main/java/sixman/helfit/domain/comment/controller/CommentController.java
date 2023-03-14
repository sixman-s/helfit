package sixman.helfit.domain.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.comment.mapper.CommentMapper;
import sixman.helfit.domain.comment.service.CommentService;
import sixman.helfit.response.ApiResponse;
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
                                      @Valid @RequestBody CommentDto.Post requestBody){
        Comment comment = mapper.commentPostToComment(requestBody);
        Comment savedComment = commentService.createComment(comment,userId,boardId);
        URI uri = UriUtil.createUri(COMMENT_DEFAULT_URI, savedComment.getCommentId());

        return ResponseEntity.created(uri).body(ApiResponse.created());
    }

    @GetMapping("{board-id}")
    public ResponseEntity getComment(@Positive @PathVariable ("board-id") long boardId){
        List<Comment> comments = commentService.getComments(boardId);
        return new ResponseEntity(mapper.commentsToCommentResponseDtos(comments),HttpStatus.OK);
    }

}
