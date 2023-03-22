package sixman.helfit.domain.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.user.controller.UserController;
import sixman.helfit.security.entity.UserPrincipal;

import java.time.LocalDateTime;

public class CalculatorDto {
    @Getter
    @Setter
    public static class Post{
        private Goal goal;
        private ActivityLevel activityLevel;

        public Post(Goal diet, ActivityLevel extraActive) {

        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private Goal goal;
        private ActivityLevel activityLevel;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private double result;
        private Long calculatorId;
        private ActivityLevel activityLevel;
        private Goal goal;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostResponse{
        private Long calculatorId;
        private Long userId;

    }
}


