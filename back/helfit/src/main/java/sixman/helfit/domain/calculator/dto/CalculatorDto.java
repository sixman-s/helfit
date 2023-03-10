package sixman.helfit.domain.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sixman.helfit.domain.calculator.enums.ActivityLevel;

import java.time.LocalDateTime;

public class CalculatorDto {
    @Getter
    @Setter
    public static class Post{
        private String goal;
        private ActivityLevel activityLevel;
    }
    @Getter
    @AllArgsConstructor
    public static class Patch{
        private long calculatorId;
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
        private long calculatorId;
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
