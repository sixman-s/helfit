package sixman.helfit.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class StatDto {

    @Getter
    @AllArgsConstructor
    public static class calendarResponse{
        private Integer kcal;
        private String recodedAt;
    }

    @Getter
    @AllArgsConstructor
    public static class boardResponse{
        private long boardId;
        private String boardImageUrl;
        private String title;
        private String text;
    }
    @Getter
    @AllArgsConstructor
    public static class physicalResponse{
        private Integer weight;
        private String lastModifiedAt;
    }

}
