package sixman.helfit.domain.chat_gpt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatGptConfig {
    public static String SECRET_KEY;
    public static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";
    public static final String COMPLETIONS_URL = "https://api.openai.com/v1/completions";
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String CHAT_MODEL = "gpt-3.5-turbo";
    public static final String COMPLETIONS_MODEL = "text-davinci-003";
    public static final Double TOP_P = 1.0;
    public static final Double TEMPERATURE = 0.2;  // 0 ~ 2
    public static final Integer MAX_TOKENS = 25;   // default: 16
    public static final Boolean STREAM = true;     // default: false

    @Value("${chat-gpt.secret-key}")
    public void setSecretKey(String value) {
       SECRET_KEY = value;
   }
}
