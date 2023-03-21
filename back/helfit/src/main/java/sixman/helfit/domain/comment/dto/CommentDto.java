package sixman.helfit.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class CommentDto {
    @Getter
    @Setter
    public static class PostAndPatch {
        @NotNull
        @Size(max = 16383)
        @NoKoreanCurseWords
        private String commentBody;
    }

    @Getter
    @AllArgsConstructor
    public static class responseDto {
        private long commentId;
        private long userId;
        private String userNickname;
        private String userProfileUrl;
        private String commentBody;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
