package sixman.helfit.domain.physical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import sixman.helfit.global.annotations.ValidEnum;

import javax.validation.constraints.*;

import static sixman.helfit.domain.physical.entity.Physical.*;

public class PhysicalDto {
    @Getter
    public static class Post {
        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @Positive
        @Digits(integer = 8, fraction = 0, message = "생년월일은 년도(4자리: 1970), 월(2자리: 01 ~ 12), 일(2자리: 01 ~ 31) 포함 8자리 숫자 형태로 입력되어야 합니다.")
        private Integer birth;

        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @Positive
        @Digits(integer = 3, fraction = 0, message = "최대 3자리 숫자 형태로 입력되어야 합니다.")
        private Integer height;

        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @Positive
        @Digits(integer = 3, fraction = 0, message = "최대 3자리 숫자 형태로 입력되어야 합니다.")
        private Integer weight;

        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        @ValidEnum(
            enumClass = Gender.class,
            message = "잘못된 입력값입니다. : 기대값: ['MALE', FEMALE]",
            ignoreCase = true
        )
        private String gender;
    }

    @Getter
    public static class Patch {
        @Nullable
        @Positive
        @Digits(integer = 8, fraction = 0, message = "생년월일은 년도(4자리: 1970), 월(2자리: 01 ~ 12), 일(2자리: 01 ~ 31) 포함 8자리 숫자 형태로 입력되어야 합니다.")
        private Integer birth;

        @Nullable
        @Positive
        @Digits(integer = 3, fraction = 0, message = "최대 3자리 숫자 형태로 입력되어야 합니다.")
        private Integer height;

        @Nullable
        @Positive
        @Digits(integer = 3, fraction = 0, message = "최대 3자리 숫자 형태로 입력되어야 합니다.")
        private Integer weight;

        @NotNull
        @ValidEnum(
            enumClass = Gender.class,
            message = "잘못된 입력값입니다. : 기대값: ['MALE', FEMALE]",
            ignoreCase = true
        )
        private String gender;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long physicalId;
        private Integer birth;
        private Integer height;
        private Integer weight;
        private Gender gender;
    }
}
