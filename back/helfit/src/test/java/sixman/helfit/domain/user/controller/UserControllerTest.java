package sixman.helfit.domain.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.mapper.UserMapper;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.security.mail.entity.EmailConfirmToken;
import sixman.helfit.security.mail.service.EmailConfirmTokenService;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.token.AuthTokenProvider;

import javax.mail.MessagingException;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {
    final String DEFAULT_URL = "/api/v1/users";

    @MockBean
    AppProperties appProperties;

    @MockBean
    AuthTokenProvider authTokenProvider;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    UserService userService;

    @MockBean
    UserMapper userMapper;

    @MockBean
    FileService fileService;

    @MockBean
    EmailConfirmTokenService emailConfirmTokenService;

    @MockBean
    UserRefreshTokenRepository userRefreshTokenRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("[테스트] 회원 가입 : LOCAL")
    void signupTest() throws Exception {
        given(userService.createUser(Mockito.any(User.class)))
            .willReturn((User) userResource().get("user"));

        given(emailConfirmTokenService.createEmailConfirmToken(Mockito.anyLong()))
            .willReturn(new EmailConfirmToken());

        Mockito.doNothing().when(emailConfirmTokenService).sendEmail(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("[테스트] 회원 정보 조회")
    @WithMockUserCustom
    void getUserTest() throws Exception {
        given(userService.findUserByUserId(Mockito.anyLong()))
            .willReturn((User) userResource().get("user"));

        given(userMapper.userToUserDtoResponse(Mockito.any(User.class)))
            .willReturn((UserDto.Response) userResource().get("userDtoResponse"));

        getResource(DEFAULT_URL)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.data").isNotEmpty())
            .andDo(restDocs.document(
                genRelaxedResponseHeaderFields("header"),
                genRelaxedResponseBodyFields("body.data")
            ));
    }

    private ResponseFieldsSnippet genRelaxedResponseHeaderFields(String beneath) {
        return relaxedResponseFields(
            beneathPath(beneath).withSubsectionId("header"),
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
        );
    }

    private ResponseFieldsSnippet genRelaxedResponseBodyFields(String beneath) {
        return relaxedResponseFields(
            beneathPath(beneath).withSubsectionId("data"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
            fieldWithPath("id").type(JsonFieldType.STRING).description("회원 아이디"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
            fieldWithPath("emailVerifiedYn").type(JsonFieldType.STRING).description("이메일 인증 여"),
            fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
            fieldWithPath("personalInfoAgreementYn").type(JsonFieldType.STRING).description("개인정보 제공 동의 여부"),
            fieldWithPath("roleType").type(JsonFieldType.STRING).description("회원 권한"),
            fieldWithPath("providerType").type(JsonFieldType.STRING).description("가입 경로"),
            fieldWithPath("userStatus").type(JsonFieldType.STRING).description("회원 상태"),
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자"),
            fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("최종 수정 일자")
        );
    }
}
