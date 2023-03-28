package sixman.helfit.domain.comment.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.comment.dto.CommentDto;
import sixman.helfit.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.PostAndPatch postDto);
    default CommentDto.ResponseDto commentToResponseDto(Comment comment) {
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
        String title = null;
        Long boardId = null;
        Long categoryId = null;

        commentBody = comment.getCommentBody();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
        userNickname = comment.getUser().getNickname();
        userProfileUrl = comment.getUser().getProfileImageUrl();
        commentId = comment.getCommentId();
        userId = comment.getUser().getUserId();
        title = comment.getBoard().getTitle();
        boardId = comment.getBoard().getBoardId();
        categoryId = comment.getBoard().getCategory().getCategoryId();

        return new CommentDto.ResponseDto(commentId,userId,categoryId,boardId,title,userNickname,
                userProfileUrl, commentBody, createdAt, modifiedAt );
    };
    List<CommentDto.ResponseDto> commentsToResponseDtos(List<Comment> comments);
}
