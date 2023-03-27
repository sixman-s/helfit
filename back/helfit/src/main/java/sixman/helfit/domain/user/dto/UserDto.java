package sixman.helfit.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.lang.Nullable;
import sixman.helfit.global.annotations.ValidEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.LocalDateTime;

import static sixman.helfit.domain.user.entity.User.*;

public class UserDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Signup {
        @NotBlank
        private String id;

        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-\"',.+/])[A-Za-z\\d!@#$%^&*()_\\-\"',.+/]{8,}$",
            message = "비밀번호는 영어(대/소문자), 숫자, 특수문자 포함 8자 이상으로 구성되어야 합니다."
        )
        private String password;

        @Pattern(regexp = "^\\S+(\\s?\\S+)*$", message = "별명은 공백을 포함할 수 없습니다.")
        private String nickname;

        @NotBlank
        @Email
        private String email;

        @NotNull
        @ValidEnum(
            enumClass = PersonalInfoAgreement.class,
            message = "개인정보 제공 동의 항목은 '필수' 입력값입니다. : 기대값 = ['Y']",
            ignoreCase = true
        )
        private String personalInfoAgreement;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        @NotBlank
        private String id;

        @NotBlank
        private String password;

        @Nullable
        @ValidEnum(
            enumClass = Activate.class,
            message = "휴면계정에서 '활성화' 계정으로 전환 시 입력 기대값입니다. : 기대값 = ['Y'] ",
            ignoreCase = true
        )
        private String activate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        @Pattern(regexp = "^\\S+(\\s?\\S+)*$", message = "별명은 공백을 포함할 수 없습니다.")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Password {
        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-\"',.+/])[A-Za-z\\d!@#$%^&*()_\\-\"',.+/]{8,}$",
            message = "비밀번호는 영어(대/소문자), 숫자, 특수문자 포함 8자 이상으로 구성되어야 합니다."
        )
        private String password;
    }


    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long userId;
        private String id;
        private String nickname;
        private String email;
        private String emailVerifiedYn;
        private String profileImageUrl;
        private String providerType;
        private String userStatus;
    }
}
