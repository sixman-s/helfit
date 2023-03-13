package sixman.helfit.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotNull
        @Positive
        private Long userId;

        @NotNull
        @Size(max = 200000)
        @NoKoreanCurseWords
        private String commentBody;

        @NotNull
        @Positive
        private Long boardId;
    }
}
