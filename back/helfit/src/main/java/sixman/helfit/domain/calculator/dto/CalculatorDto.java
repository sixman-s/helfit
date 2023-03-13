package sixman.helfit.domain.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.user.controller.UserController;
import sixman.helfit.security.entity.UserPrincipal;

import java.time.LocalDateTime;

public class CalculatorDto {
    UserPrincipal userPrincipal = new UserPrincipal();
    @Getter
    @Setter
    public static class Post{
        private String goal;
        private ActivityLevel activityLevel;
    }
    @Getter
    @AllArgsConstructor
    public static class Patch{
        private Long calculatorId;
        private double result;

        public void setCalculatorId(long calculatorId){ this.calculatorId = calculatorId;}
    }
    @Getter
    @AllArgsConstructor
    public static class GetResponse{
        private double result;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Getter
    @AllArgsConstructor
    public static class PostResponse {
        private Long calculatorId;
        private double result;
        private Long userId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Getter
    @AllArgsConstructor
    public static class PatchResponse{
        private double result;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}


