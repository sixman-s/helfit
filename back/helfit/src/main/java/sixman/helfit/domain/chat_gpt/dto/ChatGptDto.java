package sixman.helfit.domain.chat_gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class ChatGptDto {
    @Getter
    @NoArgsConstructor
    public static class Question implements Serializable {
        @NotBlank
        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        private String question;

        @Builder
        public Question(String question) {
            this.question = question;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Completions implements Serializable {
        @NotBlank
        @NotNull(message = "Null 값은 입력할 수 없습니다.")
        private String prompt;

        @Builder
        public Completions(String question) {
            this.prompt = question;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RequestQuestion implements Serializable {
        private String model;
        private List<Map<String, String>> messages;
        private Double temperature;
        @JsonProperty("top_p")
        private Double topP;
        private Boolean stream;

        @Builder
        public RequestQuestion(String model, List<Map<String, String>> messages, Double temperature, Double topP, Boolean stream) {
            this.model = model;
            this.messages = messages;
            this.temperature = temperature;
            this.topP = topP;
            this.stream = stream;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RequestCompletions implements Serializable {
        private String model;
        private String prompt;
        @JsonProperty("max_tokens")
        private Integer maxTokens;
        private Double temperature;
        @JsonProperty("top_p")
        private Double topP;

        @Builder
        public RequestCompletions(String model, String prompt, Integer maxTokens, Double temperature, Double topP) {
            this.model = model;
            this.prompt = prompt;
            this.maxTokens = maxTokens;
            this.temperature = temperature;
            this.topP = topP;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseQuestion implements Serializable {
        private String id;
        private String object;
        private String model;
        private Long created;
        private List<ResponseQuestionChoice> choices;

        @Builder
        public ResponseQuestion(String id, String object, String model, Long created, List<ResponseQuestionChoice> choices) {
            this.id = id;
            this.object = object;
            this.model = model;
            this.created = created;
            this.choices = choices;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseCompletions implements Serializable {
        private String id;
        private String object;
        private String model;
        private Long created;
        private List<ResponseCompletionsChoice> choices;
        private Map<String, String> usage;

        @Builder
        public ResponseCompletions(String id, String object, String model, Long created, List<ResponseCompletionsChoice> choices, Map<String, String> usage) {
            this.id = id;
            this.object = object;
            this.model = model;
            this.created = created;
            this.choices = choices;
            this.usage = usage;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseQuestionChoice implements Serializable {
        private Map<String, String> delta;
        private Integer index;

        @JsonProperty("finish_reason")
        private String finishReason;

        @Builder
        public ResponseQuestionChoice(Map<String, String> delta, Integer index, String finishReason) {
            this.delta = delta;
            this.index = index;
            this.finishReason = finishReason;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseCompletionsChoice implements Serializable {
        private String text;
        private Integer index;
        private String logprobs;
        @JsonProperty("finish_reason")
        private String finishReason;


        @Builder
        public ResponseCompletionsChoice(String text, Integer index, String logprobs, String finishReason) {
            this.text = text;
            this.index = index;
            this.logprobs = logprobs;
            this.finishReason = finishReason;
        }
    }

    private static String convertTimestamp(Long created) {
        Instant instant = Instant.ofEpochSecond(created);
        LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

       return String.format("%tF %<tT", datetime);
    }
}
