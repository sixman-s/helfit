package sixman.helfit.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDto {
    @Getter
    @AllArgsConstructor
    public static class Post{
        @NotNull
        @Size(max = 2000)
        private String tagName;
    }
}
