package sixman.helfit.domain.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.mapper.UserMapper;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.security.mail.service.EmailConfirmTokenService;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.token.AuthTokenProvider;

import static org.mockito.BDDMockito.given;

@WebMvcTest(UserControllerTest.class)
class UserControllerTest extends ControllerTest {
    String DEFAULT_URI = "/api/v1/users";

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
    @WithMockUserCustom
    void signupTest() {
        given(userService.createUser(Mockito.any(User.class)))
            .willReturn(userResource());
    }
}
