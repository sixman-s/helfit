package sixman.helfit.domain.comment.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.PostAndPatch postDto);
    default CommentDto.responseDto commentToResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        String commentBody = null;
        String userNickname = null;
        String userProfileUrl = null;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;
        Long commentId =null;
        Long userId = null;

        commentBody = comment.getCommentBody();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
        userNickname = comment.getUser().getNickname();
        userProfileUrl = comment.getUser().getProfileImageUrl();
        commentId = comment.getCommentId();
        userId = comment.getUser().getUserId();

        return new CommentDto.responseDto(commentId,userId,userNickname,userProfileUrl, commentBody, createdAt, modifiedAt );
    };
    List<CommentDto.responseDto> commentsToResponseDtos(List<Comment> comments);
}
