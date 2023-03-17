package sixman.helfit.domain.calculator.helper;

import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static sixman.helfit.domain.physical.entity.Physical.*;

public class CalculatorHelper {
    private final static Double BMR_CONSTANT_MALE = 88.4;
    private final static Double BMR_CONSTANT_FEMALE = 447.6;
    private final static Double BMR_CONSTANT_WEIGHT_MALE = 13.4;
    private final static Double BMR_CONSTANT_HEIGHT_MALE = 4.8;
    private final static Double BMR_CONSTANT_AGE_MALE = 5.68;
    private final static Double BMR_CONSTANT_WEIGHT_FEMALE = 9.25;
    private final static Double BMR_CONSTANT_HEIGHT_FEMALE = 3.1;
    private final static Double BMR_CONSTANT_AGE_FEMALE = 4.33;
    private final static Integer DIET_ADJUSTMENT = -440;
    private final static Integer BULK_ADJUSTMENT = 440;

    public static Integer turnAge(String birth) {
        LocalDate now = LocalDate.now();
        LocalDate parsedBirthDate = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
        int birthYear = Integer.parseInt(birth.substring(0, 4));
        int age = now.getYear() - birthYear;
        if (parsedBirthDate.plusYears(age).isAfter(now)) {
            age = age - 1;
        }
        return age;
    }

    public static double calculateBMR_Male(Physical physical) {
        Integer age = CalculatorHelper.turnAge(Integer.toString(physical.getBirth()));
        Double bmr = BMR_CONSTANT_MALE + (BMR_CONSTANT_WEIGHT_MALE * physical.getWeight())
                + (BMR_CONSTANT_HEIGHT_MALE * physical.getHeight())
                - (BMR_CONSTANT_AGE_MALE * age);
        return bmr;
    }

    public static double calculateBMR_Female(Physical physical) {
        Integer age = CalculatorHelper.turnAge(Integer.toString(physical.getBirth()));
        Double bmr = BMR_CONSTANT_FEMALE + (BMR_CONSTANT_WEIGHT_FEMALE * physical.getWeight())
                + (BMR_CONSTANT_HEIGHT_FEMALE * physical.getHeight())
                - (BMR_CONSTANT_AGE_FEMALE * age);
        return bmr;
    }

    public static double calculateResult(Double bmr, ActivityLevel activityLevel, Goal goal) {
        Double result;

        switch (activityLevel) {
            case SEDENTARY:
                result = bmr * 1.2;
                break;
            case LIGHTLY_ACTIVE:
                result = bmr * 1.375;
                break;
            case MODERATELY_ACTIVE:
                result = bmr * 1.55;
                break;
            case VERY_ACTIVE:
                result = bmr * 1.725;
                break;
            case EXTRA_ACTIVE:
                result = bmr * 1.9;
                break;
            default:
                throw new IllegalArgumentException("Invalid activity level");
        }
        if (goal.equals(Goal.DIET)) {
            result += DIET_ADJUSTMENT;
        } else if (goal.equals(Goal.BULK)) {
            result += BULK_ADJUSTMENT;
        } else if (goal.equals(Goal.KEEP))
            result += 0;

        return Math.round(result*100/100.0);

    }

    public static Double calculateResultWithGender(ActivityLevel activityLevel, Goal goal, Physical physical){
        Double bmr;
        if (physical.getGender().equals(Gender.MALE)) {
            bmr = CalculatorHelper.calculateBMR_Male(physical);
        } else {
            bmr = CalculatorHelper.calculateBMR_Female(physical);
        }
        Double result = CalculatorHelper.calculateResult(bmr, activityLevel, goal);
        return result;
    }

}
