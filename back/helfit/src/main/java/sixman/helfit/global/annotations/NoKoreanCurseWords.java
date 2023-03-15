package sixman.helfit.global.annotations;

import sixman.helfit.global.validator.KoreanCurseWordsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KoreanCurseWordsValidator.class)
public @interface NoKoreanCurseWords {
    String message() default "Korean profanity is not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}