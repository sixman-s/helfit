package sixman.helfit.domain.chat_gpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto;
import sixman.helfit.domain.chat_gpt.service.ChatGptService;
import sixman.helfit.response.ApiResponse;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
@Validated
public class ChatGptController {
    private final ChatGptService chatGptService;

    @GetMapping("/test/sse")
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                                                           .data("SSE MVC - " + LocalTime.now().toString())
                                                           .id(String.valueOf(i))
                                                           .name("sse event - mvc");
                    emitter.send(event);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }

    /*
     * # ChatGPT 챗
     *
     */
    @PostMapping("/question")
    public SseEmitter sendQuestion(@Valid @RequestBody ChatGptDto.Question requestBody) {
        SseEmitter emitter = new SseEmitter();

        Flux<ServerSentEvent<ChatGptDto.ResponseQuestion>> fluxResponseQuestion =
            chatGptService.askQuestion(requestBody.getQuestion());

        fluxResponseQuestion
            .map(event -> Optional.ofNullable(event.data()))
            .subscribe(
                data -> {
                    try {
                        assert data.isPresent();
                        emitter.send(data);
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                },
                emitter::completeWithError,
                emitter::complete
            );

        return emitter;
    }

    /*
     * # ChatGPT 다빈치
     *
     */
    @PostMapping("/completions")
    public ResponseEntity<?> sendCompletions(@RequestBody ChatGptDto.Completions requestBody) {
        ChatGptDto.ResponseCompletions responseCompletions = chatGptService.askCompletions(requestBody.getPrompt());

        return ResponseEntity.ok().body(ApiResponse.ok("data", responseCompletions));
    }
}
