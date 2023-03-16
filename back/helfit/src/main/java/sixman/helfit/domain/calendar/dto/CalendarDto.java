package sixman.helfit.domain.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class CalendarDto {
    @Getter
    public static class Post {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @Positive
        private Integer kcal;

        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$",
            message = "등록 일자는 년도(4자리: 1970), 월(2자리: 01 ~ 12), 일(2자리: 01 ~ 31) 사이 '-' 문자로 구분하여 입력되어야 합니다."
        )
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
