package sixman.helfit.domain.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.entity.UserRefreshToken;
import sixman.helfit.domain.user.mapper.UserMapper;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.security.mail.entity.EmailConfirmToken;
import sixman.helfit.security.mail.service.EmailConfirmTokenService;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.token.AuthToken;
import sixman.helfit.security.token.AuthTokenProvider;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sixman.helfit.restdocs.custom.CustomRequestFieldsSnippet.customRequestFields;
import static sixman.helfit.security.properties.AppProperties.*;

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

    private User user;
    private UserDto.Response userDtoResponse;
    private Authentication authentication;
    private Auth auth;
    private AuthToken accessToken;
    private AuthToken refreshToken;
    private UserRefreshToken userRefreshToken;

    @BeforeEach
    void setup() throws Exception {
        Map<String, Object> userResource = userResource();

        user = (User) userResource.get("user");
        userDtoResponse = (UserDto.Response) userResource.get("userDtoResponse");
        authentication = (Authentication) userResource.get("authentication");
        auth = (Auth) userResource.get("auth");
        accessToken = (AuthToken) userResource.get("accessToken");
        refreshToken = (AuthToken) userResource.get("refreshToken");
        userRefreshToken = (UserRefreshToken) userResource.get("userRefreshToken");
    }

    @Test
    @DisplayName("[테스트] 회원 가입 : LOCAL")
    void signupTest() throws Exception {
        given(userService.createUser(any(User.class)))
            .willReturn(user);

        given(userMapper.userDtoSignupToUser(any(UserDto.Signup.class)))
            .willReturn(user);

        given(emailConfirmTokenService.createEmailConfirmToken(anyLong()))
            .willReturn(new EmailConfirmToken());

        doNothing().when(emailConfirmTokenService).sendEmail(anyString(), anyString());

        postResource(
            DEFAULT_URL + "/signup",
            new UserDto.Signup(
                user.getId(),
                user.getPassword(),
                user.getNickname(),
                user.getEmail(),
                "Y"
            )
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(restDocs.document(
                customRequestFields(UserDto.Signup.class, new LinkedHashMap<>() {{
                    put("id", "회원 아이디");
                    put("password", "회원 비밀번호");
                    put("nickname", "회원 별명");
                    put("email", "회원 이메일");
                    put("personalInfoAgreement", "회원 개인정보 제공 동의 여부");
                }})
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 로그인 : LOCAL")
    @WithMockUserCustom
    void loginTest() throws Exception {
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .willReturn(authentication);

        given(userService.updateUser(anyLong(), any(User.class)))
            .willReturn(user);

        given(authTokenProvider.createAuthToken(anyString(), anyString(), any(Date.class)))
            .willReturn(accessToken);

        given(appProperties.getAuth())
            .willReturn(auth);

        given(authTokenProvider.createAuthToken(anyString(), any(Date.class)))
            .willReturn(refreshToken);

        given(userRefreshTokenRepository.findById(anyString()))
            .willReturn(userRefreshToken);

        given(userRefreshTokenRepository.saveAndFlush(any(UserRefreshToken.class)))
            .willReturn(userRefreshToken);

        postResource(DEFAULT_URL + "/login",
            new UserDto.Login(
                user.getId(),
                user.getPassword(),
                "Y"
            )
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.accessToken").isNotEmpty())
            .andDo(restDocs.document(
                customRequestFields(UserDto.Login.class, new LinkedHashMap<>() {{
                    put("id", "회원 아이디");
                    put("password", "회원 비밀번호");
                    put("activate", "휴면계정 활성화 여부, Optional");
                }}),
                relaxedResponseFields(
                    beneathPath("body").withSubsectionId("body"),
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("JWT accessToken")
                )
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 refreshToken 재발급 : 만료된 토큰")
    @WithMockUserCustom
    void refreshTokenTest() throws Exception {
        given(authTokenProvider.convertAuthToken(anyString()))
            .willReturn(accessToken);

        given(userRefreshTokenRepository.findByIdAndRefreshToken(anyString(), anyString()))
            .willReturn(userRefreshToken);

        given(appProperties.getAuth())
            .willReturn(auth);

        given(authTokenProvider.createAuthToken(anyString(), anyString(), any(Date.class)))
            .willReturn(accessToken);

        given(authTokenProvider.createAuthToken(anyString(), any(Date.class)))
            .willReturn(refreshToken);

        getResource(DEFAULT_URL + "/refresh-token")
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                relaxedResponseFields(
                    beneathPath("body").withSubsectionId("body"),
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("JWT accessToken")
                )
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 이메일 인증")
    void confirmEmailTest() throws Exception {
        given(emailConfirmTokenService.findVerifiedConfirmTokenByTokenId(anyString()))
            .willReturn(new EmailConfirmToken("token-id", 1L, false));

        doNothing().when(emailConfirmTokenService).updateEmailConfirmToken(anyString());

        doNothing().when(userService).updateUserEmailVerifiedYn(anyLong());

        getResource(DEFAULT_URL + "/confirm-email",
            new LinkedMultiValueMap<>() {{
                add("token-id", "token-id");
            }}
        )
            .andExpect(model().size(1))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                requestParameters(
                    parameterWithName("token-id").description("이메일 인증 토큰")
                )
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 이메일 인증 재발송")
    @WithMockUserCustom
    void resendConfirmEmailTest() throws Exception {
        given(emailConfirmTokenService.findVerifiedConfirmTokenByUserId(anyLong()))
            .willReturn(new EmailConfirmToken("token-id", 1L, false));

        doNothing().when(emailConfirmTokenService).sendEmail(anyString(), anyString());

        getResource(DEFAULT_URL + "/resend-confirm-email")
            .andExpect(status().isOk())
            .andDo(restDocs.document());
    }

    @Test
    @DisplayName("[테스트] 회원 정보 조회")
    @WithMockUserCustom
    void getUserTest() throws Exception {
        given(userService.findUserByUserId(anyLong()))
            .willReturn(user);

        given(userMapper.userToUserDtoResponse(any(User.class)))
            .willReturn(userDtoResponse);

        getResource(DEFAULT_URL)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.data").isNotEmpty())
            .andDo(restDocs.document(
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields()
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 정보 변경 : 별명")
    @WithMockUserCustom
    void updateUserTest() throws Exception {
        given(userService.updateUser(anyLong(), any(User.class)))
            .willReturn(user);

        given(userMapper.userDtoPatchToUser(any(UserDto.Update.class)))
            .willReturn(user);

        given(userMapper.userToUserDtoResponse(any(User.class)))
            .willReturn(
                new UserDto.Response(
                    userDtoResponse.getUserId(),
                    userDtoResponse.getId(),
                    "nickname",
                    userDtoResponse.getEmail(),
                    userDtoResponse.getEmailVerifiedYn(),
                    userDtoResponse.getProfileImageUrl(),
                    userDtoResponse.getProviderType(),
                    userDtoResponse.getUserStatus()
                )
            );

        patchResource(DEFAULT_URL, new UserDto.Update("nickname"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.data.nickname", Matchers.is("nickname")))
            .andDo(restDocs.document(
                customRequestFields(UserDto.Update.class, new LinkedHashMap<>() {{
                    put("nickname", "회원 별명");
                }}),
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields()
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 비밀번호 변경")
    @WithMockUserCustom
    void updateUserPasswordTest() throws Exception {
        doNothing().when(userService).updateUserPassword(anyLong(), any(User.class));

        patchResource(DEFAULT_URL + "/password", new UserDto.Password("Test!@#$1234"))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                customRequestFields(UserDto.Password.class, new LinkedHashMap<>() {{
                    put("password", "회원 비밀번호");
                }})
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 프로필 이미지 등록 & 수정")
    @WithMockUserCustom
    void updateUserProfileImageTest() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile(
            "multipartFile",
            "profileImage.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "profileImage".getBytes()
        );

        given(fileService.uploadFile(any(MultipartFile.class)))
            .willReturn("://ObjectStorage" + multipartFile.getOriginalFilename());

        doNothing().when(userService).updateUserProfileImage(anyLong(), anyString());

        fileResource(DEFAULT_URL + "/profile-image", multipartFile)
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                requestParts(
                    partWithName("multipartFile").description("업로드 이미지 파일")
                ),
                responseFields(
                    beneathPath("body").withSubsectionId("body"),
                    fieldWithPath("resource").type(JsonFieldType.STRING).description("회원 프로필 이미지 업로드 경로")
                )
            ));

    }

    @Test
    @DisplayName("[테스트] 회원 프로필 이미지 삭제")
    @WithMockUserCustom
    void deleteUserProfileImageTest() throws Exception {
        doNothing().when(userService).updateUserProfileImage(anyLong(), anyString());

        deleteResource(DEFAULT_URL + "/profile-image")
            .andExpect(status().isOk())
            .andDo(restDocs.document());
    }

    @Test
    @DisplayName("[테스트] 회원 탈퇴")
    @WithMockUserCustom
    void withdrawUserTest() throws Exception {
        doNothing().when(userService).withdrawUser(anyLong(), any(User.class));
        given(userMapper.userDtoPasswordToUser(any(UserDto.Password.class)))
            .willReturn(user);

        deleteResource(DEFAULT_URL + "/withdraw", new UserDto.Password("Test1234!@#$"))
            .andExpect(status().isNoContent())
            .andDo(restDocs.document());
    }

    private ResponseFieldsSnippet genRelaxedResponseHeaderFields() {
        return relaxedResponseFields(
            beneathPath("header").withSubsectionId("header"),
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
        );
    }

    private ResponseFieldsSnippet genRelaxedResponseBodyFields() {
        return relaxedResponseFields(
            beneathPath("body.data").withSubsectionId("data"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
            fieldWithPath("id").type(JsonFieldType.STRING).description("회원 아이디"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
            fieldWithPath("emailVerifiedYn").type(JsonFieldType.STRING).description("이메일 인증 여부"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 별명"),
            fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("회원 프로필 이미지").optional(),
            fieldWithPath("providerType").type(JsonFieldType.STRING).description("가입 경로"),
            fieldWithPath("userStatus").type(JsonFieldType.STRING).description("회원 상태")
        );
    }
}
