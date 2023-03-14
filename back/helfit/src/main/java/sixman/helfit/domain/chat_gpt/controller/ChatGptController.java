package sixman.helfit.domain.chat_gpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto;
import sixman.helfit.domain.chat_gpt.service.ChatGptService;
import sixman.helfit.response.ApiResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
@Validated
public class ChatGptController {
    private final ChatGptService chatGptService;

    @PostMapping("/question")
    public ResponseEntity<?> sendQuestion(@Valid @RequestBody ChatGptDto.Post requestBody) {
        ChatGptDto.Response response = chatGptService.askQuestion(requestBody.getQuestion());

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }
}
