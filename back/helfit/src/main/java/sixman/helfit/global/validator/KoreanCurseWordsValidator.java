package sixman.helfit.global.validator;

import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class KoreanCurseWordsValidator implements ConstraintValidator<NoKoreanCurseWords, String> {
    private static final Pattern KOREAN_CURSE_WORDS_PATTERN = Pattern.compile("(?i)\\b(시발|좆|병신|애미|개새|개새끼|미친)\\b");
    @Override
    public void initialize(NoKoreanCurseWords constraintAnnotation) {
        // no initialization needed
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are handled by the @NotNull constraint
        }
        return !KOREAN_CURSE_WORDS_PATTERN.matcher(value).find();
    }
}
