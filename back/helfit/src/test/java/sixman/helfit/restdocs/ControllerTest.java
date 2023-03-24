package sixman.helfit.restdocs;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.config.RestDocsConfig;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Import({RestDocsConfig.class, AopAutoConfiguration.class})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @BeforeEach
    void setUp(final WebApplicationContext ctx, final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                           .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                           .alwaysDo(MockMvcResultHandlers.print())
                           .alwaysDo(restDocs)
                           .addFilters(new CharacterEncodingFilter("UTF-8", true))
                           .build();
    }

    protected Map<String, Object> userResource() {
        Map<String, Object> userMap = new HashMap<>();

        User user = new User(
            "tester",
            "tester@testet.com",
            "tester",
            "",
            User.EmailVerified.Y,
            User.PersonalInfoAgreement.Y,
            ProviderType.LOCAL,
            RoleType.USER
        );
        user.setUserId(1L);
        user.setPassword("Test1234!@#$");
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());

        UserDto.Response userDtoResponse =
                new UserDto.Response(
                    1L,
                    user.getId(),
                    user.getNickname(),
                    user.getEmail(),
                    user.getEmailVerifiedYn().toString(),
                    user.getProfileImageUrl(),
                    user.getProviderType().toString()
                );

        userMap.put("user", user);
        userMap.put("userDtoResponse", userDtoResponse);

        return userMap;
    }

    protected <T> ResultActions postResource(String url, T body) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(body))
        );
    }

    protected <T> ResultActions postResource(String url, T body, Object... pathVariables) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.post(url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(body))
        );
    }
    protected ResultActions getResource(String url) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }

    protected ResultActions getResource(String url, Object... pathVariables) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.get(url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }

    protected ResultActions getResources(String url, MultiValueMap<String, String> parameters) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.get(url)
                .params(parameters)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }

    protected ResultActions patchResource(String url, Object... pathVariables) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.patch(url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }

    protected <T> ResultActions patchResource(String url, T body) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(body))
        );
    }
    protected <T> ResultActions patchResources(String url, T body, Object... pathVariables) throws Exception {
        return mockMvc.perform(
                RestDocumentationRequestBuilders.patch(url, pathVariables)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(body))
        );
    }

    protected <T> ResultActions deleteResource(String url, Object... pathVariables) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.delete(url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }
}
