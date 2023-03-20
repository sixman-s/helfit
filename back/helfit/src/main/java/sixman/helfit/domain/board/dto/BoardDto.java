package sixman.helfit.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import sixman.helfit.domain.comment.dto.CommentDto;

import sixman.helfit.domain.tag.dto.TagDto;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    public static class Post{

        @NotNull
        @Size(min=1, max=2000)
        @NoKoreanCurseWords
        private String title;

        @NotNull
        @Size(min=1, max=20000)
        @NoKoreanCurseWords
        private String text;

        @Nullable
        private String boardImageUrl;

        @Nullable
        private List<BoardTagDto> boardTags;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long boardId;
        private String title;
        private String text;
        private String boardImageUrl;
        private List<TagDto.GetResponse> tags;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
