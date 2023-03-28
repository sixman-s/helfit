package sixman.helfit.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TempUtil {
    private static final Map<String, String> tempPasswords = new HashMap<>();

    public static String issueTempPassword(String email) {
        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

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
