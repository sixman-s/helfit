package sixman.helfit.domain.chat_gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ChatGptDto {
    @Getter
    @NoArgsConstructor
    public static class Post implements Serializable {
        @NotBlank
        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        private String question;

        @Builder
        public Post(String question) {
            this.question = question;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Request implements Serializable {
        private String model;
        private List<Map<String, String>> messages;
        private Double temperature;

        @JsonProperty("top_p")
        private Double topP;

        @Builder
        public Request(String model, List<Map<String, String>> messages, Double temperature, Double topP) {
            this.model = model;
            this.messages = messages;
            this.temperature = temperature;
            this.topP = topP;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response implements Serializable {
        private String id;
        private String object;
        private LocalDate created;
        private String model;
        private List<ChatGptDto.ResponseChoice> choices;

        @Builder
        public Response(String id, String object, LocalDate created, String model, List<ChatGptDto.ResponseChoice> choices) {
            this.id = id;
            this.object = object;
            this.created = created;
            this.model = model;
            this.choices = choices;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseChoice implements Serializable {
        private Map<String, String> message;
        private Integer index;

        @JsonProperty("finish_reason")
        private String finishReason;

        @Builder
        public ResponseChoice(Map<String, String> message, Integer index, String finishReason) {
            this.message = message;
            this.index = index;
            this.finishReason = finishReason;
        }
    }
}
