package sixman.helfit.domain.calculator.helper;

import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculatorHelper {
    private final static double BMR_CONSTANT_MALE = 88.4;
    private final static double BMR_CONSTANT_FEMALE = 447.6;
    private final static double BMR_CONSTANT_WEIGHT_MALE = 13.4;
    private final static double BMR_CONSTANT_HEIGHT_MALE = 4.8;
    private final static double BMR_CONSTANT_AGE_MALE = 5.68;
    private final static double BMR_CONSTANT_WEIGHT_FEMALE = 9.25;
    private final static double BMR_CONSTANT_HEIGHT_FEMALE = 3.1;
    private final static double BMR_CONSTANT_AGE_FEMALE = 4.33;
    private final static int DIET_ADJUSTMENT = -440;
    private final static int BULK_ADJUSTMENT = 440;


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
    public static double calculateBMR_Male(User user) {
        CalculatorHelper helper = new CalculatorHelper();
        double bmr = BMR_CONSTANT_MALE + (BMR_CONSTANT_WEIGHT_MALE * user.getWeight())
                + (BMR_CONSTANT_HEIGHT_MALE * user.getHeight())
                - (BMR_CONSTANT_AGE_MALE * CalculatorHelper.turnAge(Integer.toString(user.getBirth())));
        return bmr;
    }

    public static double calculateBMR_Female(User user) {
        double bmr = BMR_CONSTANT_FEMALE + (BMR_CONSTANT_WEIGHT_FEMALE * user.getWeight())
                + (BMR_CONSTANT_HEIGHT_FEMALE * user.getHeight())
                - (BMR_CONSTANT_AGE_FEMALE * CalculatorHelper.turnAge(Integer.toString(user.getBirth())));
        return bmr;
    }

    public static double calculateResult(double bmr, ActivityLevel activityLevel, String goal) {
        double result;

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
        if (goal.equalsIgnoreCase("diet")) {
            result += DIET_ADJUSTMENT;
        } else if (goal.equalsIgnoreCase("bulk")) {
            result += BULK_ADJUSTMENT;
        } else if (goal.equalsIgnoreCase("keep"))
            result += 0;

        return result;
    }

}
