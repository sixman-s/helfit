package sixman.helfit.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
}
