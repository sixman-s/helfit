package sixman.helfit.domain.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;
import sixman.helfit.domain.comment.mapper.CommentMapper;
import sixman.helfit.domain.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
    @PostMapping("/{category-id}/{user-id}/{board-id}")
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody){
        Comment comment = mapper.commentPostToComment(requestBody);


        return new ResponseEntity(comment, HttpStatus.CREATED);
    }
}
