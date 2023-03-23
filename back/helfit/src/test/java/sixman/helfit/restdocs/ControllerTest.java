package sixman.helfit.restdocs;

import com.google.gson.Gson;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
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
import sixman.helfit.domain.user.entity.UserRefreshToken;
import sixman.helfit.restdocs.config.RestDocsConfig;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
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

    @Spy
    protected AppProperties appProperties;

    @Spy
    protected AuthTokenProvider authTokenProvider;

    @BeforeEach
    void setUp(final WebApplicationContext ctx, final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                           .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                           .alwaysDo(MockMvcResultHandlers.print())
                           .alwaysDo(restDocs)
                           .addFilters(new CharacterEncodingFilter("UTF-8", true))
                           .build();
    }

    protected Map<String, Object> userResource() throws Exception {
        // Base64 SecretKey inject, Testing only
        FieldUtils.writeField(authTokenProvider, "secretKey", "d297f22853e39936052a15a41266866bf058923f", true);
        FieldUtils.writeField(authTokenProvider, "key", Keys.hmacShaKeyFor("d297f22853e39936052a15a41266866bf058923f".getBytes()), true);

        Date now = new Date();
        Map<String, Object> resource = new HashMap<>();

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

        UserPrincipal userPrincipal = new UserPrincipal(
            user,
            Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
        );

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userPrincipal,
                "NO_PASS"
            );

        AuthToken accessToken = authTokenProvider.createAuthToken(
            user.getId(),
            user.getRoleType().getCode(),
            new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        AuthToken refreshToken = authTokenProvider.createAuthToken(
            user.getId(),
            new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        UserRefreshToken userRefreshToken = new UserRefreshToken(user.getId(), refreshToken.getToken());

        resource.put("user", user);
        resource.put("userDtoResponse", userDtoResponse);
        resource.put("authentication", authentication);
        resource.put("accessToken", accessToken);
        resource.put("refreshToken", refreshToken);
        resource.put("userRefreshToken", userRefreshToken);

        return resource;
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

    protected <T> ResultActions deleteResource(String url, Object... pathVariables) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.delete(url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }
}
