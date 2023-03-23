//package sixman.helfit.domain.calculator.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import net.bytebuddy.asm.Advice;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import sixman.helfit.domain.calculator.dto.CalculatorDto;
//import sixman.helfit.domain.calculator.entity.Calculator;
//import sixman.helfit.domain.calculator.enums.ActivityLevel;
//import sixman.helfit.domain.calculator.enums.Goal;
//import sixman.helfit.domain.calculator.mapper.CalculatorMapper;
//import sixman.helfit.domain.calculator.service.CalculatorService;
//import sixman.helfit.domain.physical.entity.Physical;
//import sixman.helfit.domain.physical.service.PhysicalService;
//import sixman.helfit.domain.user.entity.User;
//import sixman.helfit.restdocs.ControllerTest;
//import sixman.helfit.restdocs.annotations.WithMockUserCustom;
//import sixman.helfit.security.entity.UserPrincipal;
//
//import java.time.LocalDateTime;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CalculatorController.class)
//public class CalculatorControllerTest extends ControllerTest {
//    private String DEFAULT_URL = "/api/v1/calculate";
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CalculatorService calculatorService;
//    @MockBean
//    private CalculatorMapper calculatorMapper;
//    @MockBean
//    private CalculatorController calculatorController;
//    @MockBean
//    private UserPrincipal userPrincipal;
//    @MockBean
//    private PhysicalService physicalService;
//
//    @WithMockUserCustom
//    @Test
//    @DisplayName("[테스트] 계산기 결과 등록")
//    void postResultTest() throws Exception {
//
//        //given
//        CalculatorDto.Post requestBody = new CalculatorDto.Post(Goal.DIET,ActivityLevel.EXTRA_ACTIVE);
//        requestBody.setGoal(Goal.DIET);
//        requestBody.setActivityLevel(ActivityLevel.EXTRA_ACTIVE);
//
//        User user = new User();
//        user.setUserId(1L);
//
//        Physical physical = new Physical();
//        physical.setWeight(78);
//        physical.setHeight(173);
//        physical.setBirth(19961213);
//        physical.setGender(Physical.Gender.MALE);
//
//        Calculator calculator = new Calculator();
//        calculator.setCalculatorId(1L);
//        calculator.setResult(2600.0);
//        calculator.setUser(user);
//
//
//        given(calculatorService.createResult(
//                eq(calculator),
//                eq(user),
//                eq(physical)))
//                .willAnswer(invocation -> invocation.getArgument(0));
//        given(physicalService.findPhysicalByUserId(anyLong())).willReturn(physical);
//
//        postResource(DEFAULT_URL + "/{user-id}", requestBody,user.getUserId())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.calculatorId").value(calculator.getCalculatorId()))
//                .andExpect(jsonPath("$.data.result").value(calculator.getResult()));
//    }
//    @Test
//    @WithMockUserCustom
//    @DisplayName("[테스트] 계산기 결과 조회")
//    public void testGetResult() throws Exception {
//        Calculator calculator = new Calculator();
//        CalculatorDto.Response response = new CalculatorDto.Response(2600,1L,ActivityLevel.EXTRA_ACTIVE,Goal.DIET, LocalDateTime.now(),LocalDateTime.now());
//        when(calculatorService.findUserResult(Mockito.anyLong())).thenReturn(calculator);
//        when(calculatorMapper.calculatorToResponse(Mockito.any(Calculator.class))).thenReturn(response);
//
//        getResource(DEFAULT_URL + "/{user-id}", 1L)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.calculatorId").value(calculator.getCalculatorId()))
//                .andExpect(jsonPath("$.data.result").value(calculator.getResult()))
//                .andExpect(jsonPath("$.data.goal").value(calculator.getGoal()))
//                .andExpect(jsonPath("$.data.activityLevel").value(calculator.getActivityLevel()))
//                .andExpect(jsonPath("$.data.createdAt").value(calculator.getCreatedAt()))
//                .andExpect(jsonPath("$.data.modifiedAt").value(calculator.getModifiedAt()));
//        Mockito.verify(calculatorService, times(1)).findUserResult(Mockito.anyLong());
//        Mockito.verify(calculatorMapper, times(1)).calculatorToResponse(Mockito.any(Calculator.class));
//    }
//    @Test
//    @WithMockUserCustom
//    @DisplayName("[테스트] 계산기 수정")
//    public void testPatchResult() throws Exception {
//        // given
//        Long calculatorId = 1L;
//        CalculatorDto.Patch patchRequestBody = new CalculatorDto.Patch(Goal.KEEP, ActivityLevel.LIGHTLY_ACTIVE);
//        UserPrincipal userPrincipal = new UserPrincipal();
//
//        Calculator verifiedResult = new Calculator();
//        CalculatorDto.Response expectedResponse = new CalculatorDto.Response(
//                2000.0,
//                calculatorId,
//                ActivityLevel.LIGHTLY_ACTIVE,
//                Goal.KEEP,
//                LocalDateTime.of(2023, 3, 22, 10, 0),
//                LocalDateTime.of(2023, 3, 22, 11, 0)
//        );
//
//        when(calculatorService.findVerifiedCalculatorWithUserId(calculatorId, userPrincipal.getUser().getUserId()))
//                .thenReturn(verifiedResult);
//        when(calculatorMapper.calculatorPatchToCalculator(patchRequestBody)).thenReturn(new Calculator());
//        when(calculatorService.updateCalculator(any(Calculator.class), eq(verifiedResult))).thenReturn(new Calculator());
//        when(calculatorService.updateResult(any(Calculator.class), any(User.class), any(Physical.class))).thenReturn(new Calculator());
//        when(calculatorMapper.calculatorToResponse(any(Calculator.class))).thenReturn(expectedResponse);
//
//        // when
//        patchResource(DEFAULT_URL + "/{calculator-id}", calculatorId)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code", is("200")))
//                .andExpect(jsonPath("$.message", is("OK")))
//                .andExpect(jsonPath("$.data.result", is(2000.0)))
//                .andExpect(jsonPath("$.data.calculatorId", is(1)))
//                .andExpect(jsonPath("$.data.activityLevel", is("LIGHTLY_ACTIVE")))
//                .andExpect(jsonPath("$.data.goal", is("MAINTAIN")))
//                .andExpect(jsonPath("$.data.createdAt", is("2023-03-22T10:00:00")))
//                .andExpect(jsonPath("$.data.modifiedAt", is("2023-03-22T11:00:00")));
//
//        // then
//        verify(calculatorService, times(1)).findVerifiedCalculatorWithUserId(calculatorId, userPrincipal.getUser().getUserId());
//        verify(calculatorMapper, times(1)).calculatorPatchToCalculator(patchRequestBody);
//        verify(calculatorService, times(1)).updateCalculator(any(Calculator.class), eq(verifiedResult));
//        verify(calculatorService, times(1)).updateResult(any(Calculator.class), any(User.class), any(Physical.class));
//        verify(calculatorMapper, times(1)).calculatorToResponse(any(Calculator.class));
//    }
//    @Test
//    @WithMockUserCustom
//    @DisplayName("[테스트] 계산기 삭제")
//    public void testDeleteResult() throws Exception {
//        User user = new User();
//        user.setUserId(1L);
//        doNothing().when(calculatorService).deleteResult(anyLong());
//
//        ResultActions actions = mockMvc.perform(
//                delete(DEFAULT_URL + "/{user-id}", 1L)
//        );
//        deleteResource(DEFAULT_URL + "{user-id}",1L)
//                .andExpect(status().isNoContent());
//    }
//}

