package sixman.helfit.domain.comment.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.PostAndPatch postDto);
    CommentDto.responseDto commentToResponseDto(Comment comment);
    List<CommentDto.responseDto> commentsToResponseDtos(List<Comment> comments);
}
