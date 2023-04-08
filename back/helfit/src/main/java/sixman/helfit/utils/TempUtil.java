package sixman.helfit.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TempUtil {
    private static final Map<String, String> tempPasswords = new HashMap<>();
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";

    public static String issueTempPassword(String email) {
        // 임시 비밀번호 생성
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(PASSWORD_CHARS.charAt(random.nextInt(PASSWORD_CHARS.length())));
        }
        String tempPassword = password.toString();

        // 임시 비밀번호 저장
        tempPasswords.put(email, tempPassword);

        // 60분 후에 자동으로 삭제될 수 있도록 스케줄링
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    tempPasswords.remove(email);
                }
            },
            TimeUnit.MINUTES.toMillis(60)
        );

        return tempPasswords.get(email);
    }
}
