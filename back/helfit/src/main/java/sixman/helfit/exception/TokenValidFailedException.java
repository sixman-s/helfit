package sixman.helfit.exception;

public class TokenValidFailedException extends RuntimeException {
    public TokenValidFailedException() {
        super("토큰 생성에 실패했습니다.");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
