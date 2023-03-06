package sixman.helfit.domain.calculator;

import sixman.helfit.domain.user2.entity.User;
import sixman.helfit.domain.user2.enums.ActivityLevel;

public class Calculator {
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


    public static double calculateBMR_Male(User user) {
        double bmr = BMR_CONSTANT_MALE + (BMR_CONSTANT_WEIGHT_MALE * user.getWeight())
                + (BMR_CONSTANT_HEIGHT_MALE * user.getHeight())
                - (BMR_CONSTANT_AGE_MALE * user.getAge());
        return bmr;
    }

    public static double calculateBMR_Female(User user) {
        double bmr = BMR_CONSTANT_FEMALE + (BMR_CONSTANT_WEIGHT_FEMALE * user.getWeight())
                + (BMR_CONSTANT_HEIGHT_FEMALE * user.getHeight())
                - (BMR_CONSTANT_AGE_FEMALE * user.getAge());
        return bmr;
    }

    public static double calculateHb(double bmr, ActivityLevel activityLevel, String goal) {
        double hb;

        switch (activityLevel) {
            case SEDENTARY:
                hb = bmr * 1.2;
                break;
            case LIGHTLY_ACTIVE:
                hb = bmr * 1.375;
                break;
            case MODERATELY_ACTIVE:
                hb = bmr * 1.55;
                break;
            case VERY_ACTIVE:
                hb = bmr * 1.725;
                break;
            case EXTRA_ACTIVE:
                hb = bmr * 1.9;
                break;
            default:
                throw new IllegalArgumentException("Invalid activity level");
        }
        if (goal.equalsIgnoreCase("diet")) {
            hb += DIET_ADJUSTMENT;
        } else if (goal.equalsIgnoreCase("bulk")) {
            hb += BULK_ADJUSTMENT;
        } else if (goal.equalsIgnoreCase("keep"))
            hb += 0;

        return hb;
    }
}
