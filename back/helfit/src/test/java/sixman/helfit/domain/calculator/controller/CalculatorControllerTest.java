package sixman.helfit.domain.calculator.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sixman.helfit.domain.calculator.dto.CalculatorDto;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.calculator.mapper.CalculatorMapper;
import sixman.helfit.domain.calculator.service.CalculatorService;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.service.PhysicalService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest extends ControllerTest {
    private String DEFAULT_URL = "/api/v1/calculate";

    @MockBean
    PhysicalService physicalService;
    @MockBean
    CalculatorService calculatorService;

    @MockBean
    CalculatorMapper calculatorMapper;
    @MockBean
    CalculatorDto calculatorDto;

    private User user;
    private Physical physical;
    private Calculator calculator;
    private CalculatorDto.Response calculatorDtoResponse;

    @BeforeEach
    void setup() {
        Map<String, Object> userResource = userResource();
        user = (User) userResource.get("user");
        physical = new Physical(1L, 19961213, 173, 78, Physical.Gender.MALE, user);
        calculator = new Calculator(1L, Goal.DIET, ActivityLevel.VERY_ACTIVE, 3011.0, user, physical);
        calculatorDtoResponse = new CalculatorDto.Response(3011.0, 1L, ActivityLevel.VERY_ACTIVE,
                Goal.DIET, LocalDateTime.now(), LocalDateTime.now());
    }

    @WithMockUserCustom
    @Test
    @DisplayName("[테스트] 계산기 결과 등록")
    void postResultTest() throws Exception {

        CalculatorDto.Post requestBody = new CalculatorDto.Post(Goal.DIET, ActivityLevel.EXTRA_ACTIVE);


        given(calculatorService.createResult(Mockito.any(Calculator.class), Mockito.any(User.class), Mockito.any(Physical.class)))
                .willReturn(calculator);
        given(calculatorMapper.calculatorPostToCalculator(Mockito.any(CalculatorDto.Post.class)))
                .willReturn(calculator);
        given(physicalService.findPhysicalByUserId(anyLong()))
                .willReturn(physical);
        given(calculatorMapper.calculatorToResponse(Mockito.any(Calculator.class)))
                .willReturn(calculatorDtoResponse);

        postResource(DEFAULT_URL + "/{user-id}", requestBody, user.getUserId())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body.data").isNotEmpty());
    }

    @WithMockUserCustom
    @Test
    @DisplayName("[테스트] 계산기 결과 조회")
    void getResultTest() throws Exception {

        given(calculatorService.findUserResult(anyLong()))
                .willReturn(calculator);
        given(calculatorMapper.calculatorToResponse(Mockito.any(Calculator.class)))
                .willReturn(calculatorDtoResponse);
        getResource(DEFAULT_URL + "/{user-id}", 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty());
    }

    @WithMockUserCustom
    @Test
    @DisplayName("[테스트] 계산기 결과 수정")
    void patchResultTest() throws Exception {
        CalculatorDto.Patch requestBody = new CalculatorDto.Patch(Goal.BULK, ActivityLevel.EXTRA_ACTIVE);

        Calculator updatedCalculator = new Calculator(1L, Goal.BULK, ActivityLevel.EXTRA_ACTIVE, 3451.0, user, physical);

        given(calculatorService.findVerifiedCalculatorWithUserId(Mockito.anyLong(), Mockito.anyLong()))
                .willReturn(calculator);
        given(calculatorMapper.calculatorPatchToCalculator(Mockito.any(CalculatorDto.Patch.class)))
                .willReturn(calculator);
        given(calculatorService.updateCalculator(Mockito.any(Calculator.class), Mockito.any(Calculator.class)))
                .willReturn(calculator);
        given(calculatorService.updateResult(Mockito.any(Calculator.class), Mockito.any(User.class), Mockito.any(Physical.class)))
                .willReturn(updatedCalculator);
        given(physicalService.findPhysicalByUserId(Mockito.anyLong()))
                .willReturn(physical);
        given(calculatorMapper.calculatorToResponse(Mockito.any(Calculator.class)))
                .willReturn(calculatorDtoResponse);
        patchResources(DEFAULT_URL + "/{calculator-id}", requestBody, calculator.getCalculatorId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty());
    }

    @WithMockUserCustom
    @Test
    @DisplayName("[테스트] 계산기 결과 삭제")
    void deleteResultTest() throws Exception {
        User user = new User();
        user.setUserId(1L);

        doNothing().when(calculatorService).deleteResult(anyLong());


        deleteResource(DEFAULT_URL + "/{user-id}", user.getUserId())
                .andExpect(status().isNoContent());

    }
}
