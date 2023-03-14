package sixman.helfit.domain.comment.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.Post postDto);
    CommentDto.CommentResponseDto commentToCommentResponseDto(Comment comment);
    List<CommentDto.CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments);
}
