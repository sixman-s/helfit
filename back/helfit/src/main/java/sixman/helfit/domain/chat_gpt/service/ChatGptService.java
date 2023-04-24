package sixman.helfit.domain.chat_gpt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import sixman.helfit.domain.chat_gpt.config.ChatGptConfig;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import static sixman.helfit.domain.chat_gpt.config.ChatGptConfig.SECRET_KEY;

@Service
public class ChatGptService {
    WebClient webClient = WebClient.create();
    private static final RestTemplate restTemplate = new RestTemplate();

    private <T> HttpEntity<T> buildRequest(T chatGptRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + SECRET_KEY);

        return new HttpEntity<>(chatGptRequest, headers);
    }

    public Flux<ServerSentEvent<ChatGptDto.ResponseQuestion>> askQuestion(String question) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ChatGptDto.RequestQuestion build =
                ChatGptDto.RequestQuestion.builder()
                    .model(ChatGptConfig.CHAT_MODEL)
                    .messages(List.of(
                        new HashMap<>() {{
                            put("role", "user");
                            put("content", question);
                        }}
                    ))
                    .temperature(ChatGptConfig.TEMPERATURE)
                    .topP(ChatGptConfig.TOP_P)
                    .stream(ChatGptConfig.STREAM)
                    .build();

            return webClient.post()
                       .uri(ChatGptConfig.CHAT_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .header(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + SECRET_KEY)
                       .header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE)
                       .body(BodyInserters.fromValue(objectMapper.writeValueAsString(build)))
                       .retrieve()
                       .bodyToFlux(ChatGptDto.ResponseQuestion.class)
                       .subscribeOn(Schedulers.boundedElastic())
                       .onErrorComplete()
                       .map(message -> ServerSentEvent.<ChatGptDto.ResponseQuestion>builder()
                                           .event("message")
                                           .data(message)
                                           .build()
                       );
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ChatGptDto.ResponseCompletions askCompletions(String prompt) {
        ResponseEntity<?> response = restTemplate.postForEntity(
            ChatGptConfig.COMPLETIONS_URL,
            buildRequest(
                new ChatGptDto.RequestCompletions(
                    ChatGptConfig.COMPLETIONS_MODEL,
                    prompt,
                    ChatGptConfig.MAX_TOKENS,
                    ChatGptConfig.TEMPERATURE,
                    ChatGptConfig.TOP_P
                )
            ),
            ChatGptDto.ResponseCompletions.class
        );

        return (ChatGptDto.ResponseCompletions) response.getBody();
    }
}
