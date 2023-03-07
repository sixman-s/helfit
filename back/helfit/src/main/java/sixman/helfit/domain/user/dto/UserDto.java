package sixman.helfit.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.global.annotations.ValidEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static sixman.helfit.domain.user.entity.User.*;

public class UserDto {
    @Getter
    public static class Signup {
        @NotBlank
        private String id;

        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-\"',.+/])[A-Za-z\\d!@#$%^&*()_\\-\"',.+/]{8,}$",
            message = "비밀번호는 영어(대/소문자), 숫자, 특수문자 포함 8자 이상으로 구성되어야 합니다."
        )
        private String password;

        @Pattern(regexp = "^\\S+(\\s?\\S+)*$", message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @NotBlank
        private String nickname;

        @NotBlank
        @Email
        private String email;

        @Nullable
        private String profileImageUrl;

        @Nullable
        private Integer birth;

        @Nullable
        private Integer height;

        @Nullable
        private Integer weight;

        @Nullable
        @ValidEnum(enumClass = Gender.class)
        private Gender gender;
    }

    @Getter
    public static class Login {
        @NotBlank
        private String id;

        @NotBlank
        private String password;
    }

    @Getter
    public static class Patch {
        @Nullable
        @Email
        private String email;

        @Nullable
        private String nickname;

        @Nullable
        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-\"',.+/])[A-Za-z\\d!@#$%^&*()_\\-\"',.+/]{8,}$",
            message = "비밀번호는 영어(대/소문자), 숫자, 특수문자 포함 8자 이상으로 구성되어야 합니다."
        )
        private String password;

        @Nullable
        private String profileImageUrl;

        @Nullable
        private Integer birth;

        @Nullable
        private Integer height;

        @Nullable
        private Integer weight;

        @Nullable
        @ValidEnum(enumClass = Gender.class)
        private Gender gender;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private Long userId;
        private String id;
        private String name;
        private String nickname;
        private String email;
        private String emailVerifiedYn;
        private String profileImageUrl;
        private Integer birth;
        private Integer height;
        private Integer weight;
        private Gender gender;
        private String providerType;
    }
}
