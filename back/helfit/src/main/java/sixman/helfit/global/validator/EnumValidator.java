package sixman.helfit.global.validator;

import sixman.helfit.global.annotations.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;

        Object[] enumConstants = annotation.enumClass().getEnumConstants();

        if (value != null) {
            for (Object v : enumConstants) {
                if (value.equals(v.toString()) || (this.annotation.ignoreCase() && value.equalsIgnoreCase(v.toString()))) {
                    result = true;
                    break;
                }
            }
        }

        return value == null || result;
    }
}
