package sixman.helfit.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class BoardTagDto {
    @NoKoreanCurseWords
    @Size(max = 2000)
    private String tagName;
}
