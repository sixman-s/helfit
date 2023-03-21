package sixman.helfit.domain.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
import sixman.helfit.security.mail.service.EmailConfirmTokenService;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.token.AuthTokenProvider;

import java.util.Map;

import static org.mockito.BDDMockito.given;

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
    void signupTest() {
        // given(userService.createUser(Mockito.any(User.class)))
        //     .willReturn((User) userResource().get("user"));
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
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
