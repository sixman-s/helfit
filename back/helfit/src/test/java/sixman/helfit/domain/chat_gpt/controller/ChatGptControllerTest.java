package sixman.helfit.domain.chat_gpt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto.ResponseQuestionChoice;
import sixman.helfit.domain.chat_gpt.service.ChatGptService;
import sixman.helfit.restdocs.ControllerTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sixman.helfit.restdocs.custom.CustomRequestFieldsSnippet.customRequestFields;

@WebMvcTest(ChatGptController.class)
class ChatGptControllerTest extends ControllerTest {
    final String DEFAULT_URL = "/api/v1/ai";

    @MockBean
    ChatGptService chatGptService;

    private ChatGptDto.ResponseQuestion chatGptDtoResponse;
    private ServerSentEvent<ChatGptDto.ResponseQuestion> message;

    @BeforeEach
    void setup() {
        List<ResponseQuestionChoice> choices =
            List.of(
                ResponseQuestionChoice.builder()
                    .delta(
                        new HashMap<>() {{
                            put("content", "-");
                        }}
                    )
                    .finishReason("null")
                    .index(0)
                    .build()
            );

        chatGptDtoResponse = ChatGptDto.ResponseQuestion.builder()
                                 .id("chatcmpl-6yK7AzSYHL8JlZ6AIPk4Yz01McXJp")
                                 .object("chat.completion")
                                 .created(123L)
                                 .model("gpt-3.5-turbo-0301")
                                 .choices(choices)
                                 .build();

        message = ServerSentEvent.<ChatGptDto.ResponseQuestion>builder()
                      .event("message")
                      .data(chatGptDtoResponse)
                      .build();
    }

    @Test
    @DisplayName("[테스트] ChatGPT 질문")
    void sendQuestionTest() throws Exception {
        given(chatGptService.askQuestion(anyString()))
            .willReturn(Flux.just(message));

        postResource(DEFAULT_URL + "/question", new ChatGptDto.Question("안녕?"))
            .apply(false)
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM))
            .andDo(restDocs.document(
                customRequestFields(ChatGptDto.Question.class, new LinkedHashMap<>() {{
                    put("question", "질문 내용, String");
                }})
                // relaxedResponseFields(
                //     beneathPath("data").withSubsectionId("data"),
                //     fieldWithPath("id").type(JsonFieldType.STRING).description("챗봇 식별자"),
                //     fieldWithPath("object").type(JsonFieldType.STRING).description("챗봇 오브젝트"),
                //     fieldWithPath("created").type(JsonFieldType.STRING).description("메시지 생성일자"),
                //     fieldWithPath("model").type(JsonFieldType.STRING).description("챗봇 모델"),
                //     fieldWithPath("choices[].delta").type(JsonFieldType.OBJECT).description("챗봇 응답 결과"),
                //     fieldWithPath("choices[].delta.content").type(JsonFieldType.STRING).description("챗봇 응답 메세지"),
                //     fieldWithPath("choices[].index").type(JsonFieldType.NUMBER).description("챗봇 응답 식별자"),
                //     fieldWithPath("choices[].finish_reason").type(JsonFieldType.STRING).description("챗봇 응답 종료 여부")
                // )
            ));
    }
}
