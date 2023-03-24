package sixman.helfit.restdocs;

import com.google.gson.Gson;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.*;

import static sixman.helfit.security.properties.AppProperties.*;
import static sixman.helfit.utils.HeaderUtil.getAccessToken;


@Import({RestDocsConfig.class, AopAutoConfiguration.class})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerTest {
    // Base64 SecretKey inject, Testing only
    private final String TEST_SECRET_TOKEN = "d297f22853e39936052a15a41266866bf058923f";

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
        Map<String, Object> resource = new HashMap<>();

        User user = new User(
            1L,
            "TEST",
            "Test1234!@#$",
            "TEST@TEST.com",
            "TEST",
            "",
            LocalDateTime.now(),
            User.EmailVerified.Y,
            User.PersonalInfoAgreement.Y,
            RoleType.USER,
            ProviderType.LOCAL,
            User.UserStatus.USER_ACTIVE
        );
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
                    user.getProviderType().toString(),
                    user.getUserStatus().toString()
                );

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                new UserPrincipal(
                    user,
                    Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
                ),
                "NO_PASS"
            );

        Auth auth = new Auth(TEST_SECRET_TOKEN, 1000 * 60, 1000 * 60);
        AuthToken accessToken = genJwtToken("access");
        AuthToken refreshToken = genJwtToken("refresh");

        UserRefreshToken userRefreshToken = new UserRefreshToken(user.getId(), Objects.requireNonNull(refreshToken).getToken());

        resource.put("user", user);
        resource.put("userDtoResponse", userDtoResponse);
        resource.put("authentication", authentication);
        resource.put("auth", auth);
        resource.put("accessToken", accessToken);
        resource.put("refreshToken", refreshToken);
        resource.put("userRefreshToken", userRefreshToken);

        return resource;
    }

    private AuthToken genJwtToken(String type) throws Exception {
        long expiry = new Date().getTime();

        FieldUtils.writeField(authTokenProvider, "secretKey", TEST_SECRET_TOKEN, true);
        FieldUtils.writeField(authTokenProvider, "key", Keys.hmacShaKeyFor(TEST_SECRET_TOKEN.getBytes()), true);

        AuthToken accessToken =
            authTokenProvider
                .createAuthToken("TEST", RoleType.USER.getCode(), new Date(expiry));
        AuthToken refreshToken =
            authTokenProvider
                .createAuthToken("TEST", new Date(expiry));

        switch (type) {
            case "access":
                return accessToken;
            case "refresh":
                return refreshToken;
            default:
                return null;
        }
    }

    protected <T> ResultActions postResource(String url) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + Objects.requireNonNull(genJwtToken("access")).getToken())
                .cookie(new Cookie("refresh_token", Objects.requireNonNull(genJwtToken("refresh")).getToken()))
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

    protected ResultActions getResource(String url, MultiValueMap<String, String> parameters) throws Exception {
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

    protected ResultActions deleteResource(String url, Object... pathVariables) throws Exception {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.delete(url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }
}
