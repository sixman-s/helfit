package sixman.helfit.domain.calendar.dto;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class CalendarDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @NotBlank
        private String title;

        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @NotBlank
        private String content;

        @NotNull(message = "Null 값은 입력할 수 없습니다.")
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        @Nullable
        private String title;

        @Nullable
        private String content;

        @Nullable
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
    }
}
