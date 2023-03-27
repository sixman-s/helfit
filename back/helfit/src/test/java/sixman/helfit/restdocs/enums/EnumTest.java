package sixman.helfit.restdocs.enums;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.custom.CustomResponseFieldsSnippet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnumController.class)
public class EnumTest extends ControllerTest {

    @Test
    @DisplayName("[테스트] Restdocs Enum 생성")
    void enums() throws Exception {
        ResultActions action = getResource("/api/v1/test/enums").apply(false);
        MvcResult result = action.andReturn();
        EnumDocs enums = getData(result);

        action.andExpect(status().isOk())
            .andDo(restDocs.document(
                customResponseFields("custom-response",
                    beneathPath("data.userStatus").withSubsectionId("userStatus"),
                    attributes(key("title").value("userStatus")),
                    enumConvertToFieldDescriptor(enums.getUserStatus())
                )
            ));
    }

    public static CustomResponseFieldsSnippet customResponseFields(
        String type,
        PayloadSubsectionExtractor<?> subsectionExtractor,
        Map<String, Object> attributes, FieldDescriptor... descriptors
    ) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes, true);
    }

    private static FieldDescriptor[] enumConvertToFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                   .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                   .toArray(FieldDescriptor[]::new);
    }

    private EnumDocs getData(MvcResult result) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        EnumDto<EnumDocs> enumDto = objectMapper.readValue(
            result.getResponse().getContentAsByteArray(),
            new TypeReference<>() {}
        );

        return enumDto.getData();
    }
}
