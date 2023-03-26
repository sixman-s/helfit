package sixman.helfit.domain.chat_gpt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto;
import sixman.helfit.domain.chat_gpt.dto.ChatGptDto.ResponseChoice;
import sixman.helfit.domain.chat_gpt.service.ChatGptService;
import sixman.helfit.restdocs.ControllerTest;

import java.time.LocalDate;
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

    private ChatGptDto.Response chatGptDtoResponse;

    @BeforeEach
    void setup() {
        List<ResponseChoice> choices = new ArrayList<>() {{
            add(ResponseChoice.builder()
                    .message(new HashMap<>() {{
                        put("role", "assistant");
                        put("content", "하세요! 저는 AI 어시스턴트입니다. 무엇을 도와드릴까요?");
                    }})
                    .finishReason("stop")
                    .index(0)
                    .build()
            );
        }};

        chatGptDtoResponse = ChatGptDto.Response.builder()
                                 .id("chatcmpl-6yK7AzSYHL8JlZ6AIPk4Yz01McXJp")
                                 .object("chat.completion")
                                 .created(LocalDate.now())
                                 .model("gpt-3.5-turbo-0301")
                                 .choices(choices)
                                 .build();
    }

    @Test
    @DisplayName("[테스트] ChatGPT 질문")
    void sendQuestion() throws Exception {
        given(chatGptService.askQuestion(anyString()))
            .willReturn(chatGptDtoResponse);

        postResource(DEFAULT_URL + "/question", new ChatGptDto.Post("안녕?"))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                customRequestFields(ChatGptDto.Post.class, new LinkedHashMap<>() {{
                    put("question", "질문 내용, String");
                }}),
                relaxedResponseFields(
                    beneathPath("header").withSubsectionId("header"),
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                ),
                relaxedResponseFields(
                    beneathPath("body.data").withSubsectionId("data"),
                    fieldWithPath("id").type(JsonFieldType.STRING).description("챗봇 식별자"),
                    fieldWithPath("object").type(JsonFieldType.STRING).description("챗봇 오브젝트"),
                    fieldWithPath("created").type(JsonFieldType.STRING).description("메시지 생성일자"),
                    fieldWithPath("model").type(JsonFieldType.STRING).description("챗봇 모델"),
                    fieldWithPath("choices[].message").type(JsonFieldType.OBJECT).description("챗봇 응답 결과"),
                    fieldWithPath("choices[].message.role").type(JsonFieldType.STRING).description("챗봇 응답 형태"),
                    fieldWithPath("choices[].message.content").type(JsonFieldType.STRING).description("챗봇 응답 메세지"),
                    fieldWithPath("choices[].index").type(JsonFieldType.NUMBER).description("챗봇 응답 식별자"),
                    fieldWithPath("choices[].finish_reason").type(JsonFieldType.STRING).description("챗봇 응답 종료 여부")
                )
            ));
    }
}
