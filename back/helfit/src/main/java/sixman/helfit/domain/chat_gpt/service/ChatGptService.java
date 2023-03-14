package sixman.helfit.domain.chat_gpt.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sixman.helfit.domain.chat_gpt.config.ChatGptConfig;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {
    private static final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<ChatGptDto.Request> buildRequest(ChatGptDto.Request chatGptRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.SECRET_KEY);

        return new HttpEntity<>(chatGptRequest, headers);
    }

    private ChatGptDto.Response getResponse(HttpEntity<ChatGptDto.Request> aiEntity) {
        ResponseEntity<?> response = restTemplate.postForEntity(
            ChatGptConfig.URL,
            aiEntity,
            ChatGptDto.Response.class
        );

        return (ChatGptDto.Response) response.getBody();
    }

    public ChatGptDto.Response askQuestion(String question) {
        Map<String, String> messages = new HashMap<>() {{
            put("role", "user");
            put("content", question);
        }};

        return getResponse(
            buildRequest(
                new ChatGptDto.Request(
                    ChatGptConfig.MODEL,
                    List.of(messages),
                    ChatGptConfig.TEMPERATURE,
                    ChatGptConfig.TOP_P
                )
            )
        );
    }
}
