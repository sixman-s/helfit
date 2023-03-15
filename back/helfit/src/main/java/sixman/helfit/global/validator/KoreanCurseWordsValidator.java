package sixman.helfit.global.validator;

import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class KoreanCurseWordsValidator implements ConstraintValidator<NoKoreanCurseWords, String> {

    private static final List<String> bannedWords = Arrays.asList("시발", "애미", "좆", "씨발", "개새끼","개새","미친","존나");

    @Override
    public void initialize(NoKoreanCurseWords constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (String bannedWord : bannedWords) {
            if (value.contains(bannedWord)) {
                return false;
            }
        }
        return true;
    }
}
