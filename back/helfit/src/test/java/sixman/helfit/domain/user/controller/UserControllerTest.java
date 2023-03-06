package sixman.helfit.domain.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import sixman.helfit.restdocs.ControllerTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserControllerTest.class)
class UserControllerTest extends ControllerTest {

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("[테스트] 회원")
    void getUsers() {

    }
}
