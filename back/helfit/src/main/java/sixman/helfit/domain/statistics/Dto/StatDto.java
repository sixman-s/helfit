package sixman.helfit.domain.statistics.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
