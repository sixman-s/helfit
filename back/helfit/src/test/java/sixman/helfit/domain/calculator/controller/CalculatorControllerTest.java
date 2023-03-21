package sixman.helfit.domain.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sixman.helfit.domain.calculator.dto.CalculatorDto;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.calculator.service.CalculatorService;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.service.PhysicalService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.security.entity.UserPrincipal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CalculatorControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @MockBean
    private PhysicalService physicalService;

    @WithMockUserCustom
    @Test
    @DisplayName("[테스트] 계산기 결과 등록")
    void postResultTest() throws Exception {
        //given
        CalculatorDto.Post requestBody = new CalculatorDto.Post();
        requestBody.setGoal(Goal.DIET);
        requestBody.setActivityLevel(ActivityLevel.EXTRA_ACTIVE);

        User user = new User();
        user.setUserId(1L);
        UserPrincipal userPrincipal = UserPrincipal.create(user);

        Physical physical = new Physical();
        physical.setWeight(78);
        physical.setHeight(173);
        physical.setBirth(1996-12-13);
        physical.setGender(Physical.Gender.MALE);

        Calculator calculator = new Calculator();
        calculator.setCalculatorId(1L);
        calculator.setResult(2600.0);



        given(calculatorService.createResult(any(Calculator.class), any(User.class), any(Physical.class)))
                .willReturn(calculator);
        given(physicalService.findPhysicalByUserId(anyLong())).willReturn(physical);

        //when/then
        mockMvc.perform(post("/api/v1/calculate/{user-id}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.calculatorId").value(calculator.getCalculatorId()))
                .andExpect(jsonPath("$.data.result").value(calculator.getResult()));
    }


}
