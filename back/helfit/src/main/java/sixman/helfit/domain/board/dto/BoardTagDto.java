package sixman.helfit.domain.board.dto;

import lombok.Getter;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.constraints.Size;

@Getter
public class BoardTagDto {
    @NoKoreanCurseWords
    @Size(max = 2000)
    private String tagName;
}
