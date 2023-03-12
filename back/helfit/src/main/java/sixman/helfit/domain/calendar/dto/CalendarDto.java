package sixman.helfit.domain.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CalendarDto {
    @Getter
    public static class Post {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private Integer kcal;

        @NotBlank
        @Size(min = 8, max = 8)
        private String recodedAt;
    }

    @Getter
    public static class Patch {
        private String title;
        private String content;
        private Integer kcal;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long calendarId;
        private String title;
        private String content;
        private Integer kcal;
        private String recodedAt;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
