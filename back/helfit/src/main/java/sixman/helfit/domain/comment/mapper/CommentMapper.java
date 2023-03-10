package sixman.helfit.domain.comment.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.Post postDto);
}
