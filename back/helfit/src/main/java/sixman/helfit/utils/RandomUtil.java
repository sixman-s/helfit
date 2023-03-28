package sixman.helfit.utils;

import java.util.Random;

public class RandomUtil {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 6;

    private static final Random RANDOM = new Random();

    public static String generateVerificationCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));

        return sb.toString();
    }
}
