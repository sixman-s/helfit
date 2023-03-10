package sixman.helfit.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDto {
    @Getter
    @Setter
    public static class Get {
        @NotNull
        @Size(max = 2000)
        private String tagName;
    }
    @Getter
    @AllArgsConstructor
    public static class GetResponse{
        private Long tagId;
        private String tagName;
    }
}
