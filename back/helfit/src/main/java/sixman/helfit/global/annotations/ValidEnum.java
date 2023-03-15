package sixman.helfit.global.annotations;

import sixman.helfit.global.validator.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Must be any of enum ${enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean ignoreCase() default false;
}
