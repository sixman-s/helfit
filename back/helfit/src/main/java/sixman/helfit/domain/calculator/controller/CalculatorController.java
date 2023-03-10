package sixman.helfit.domain.calculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.calculator.dto.CalculatorDto;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.mapper.CalculatorMapper;
import sixman.helfit.domain.calculator.service.CalculatorService;
import sixman.helfit.domain.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final UserService userService;
    private final CalculatorMapper calculatorMapper;

    public CalculatorController(CalculatorService calculatorService, UserService userService, CalculatorMapper calculatorMapper) {
        this.calculatorService = calculatorService;
        this.userService = userService;
        this.calculatorMapper = calculatorMapper;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{user-id}")
    public ResponseEntity postResult(@AuthenticationPrincipal @RequestBody CalculatorDto.Post requestBody) {
        Calculator calculator = calculatorService.createResult(calculatorMapper.calculatorPostToCalculator(requestBody)
        ,requestBody.getActivityLevel(), requestBody.getGoal());
        return new ResponseEntity<>(calculatorMapper.calculatorToPostResponse(calculator), HttpStatus.CREATED);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{user-id}")
    public ResponseEntity<CalculatorDto.GetResponse> getResult(@PathVariable("user-id") @Positive long userId) {
        Calculator findResult = calculatorService.findUserResult(userId);
        return new ResponseEntity<>(calculatorMapper.calculatorToGetResponse(findResult), HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{user-id}")
    public ResponseEntity<CalculatorDto.PatchResponse> patchResult(@PathVariable("calculator-id") @Positive long calculatorId,
                                                                   @Valid @RequestBody CalculatorDto.Patch calculatorPatchDto){
        calculatorPatchDto.setCalculatorId(calculatorId);
        Calculator calculator = calculatorMapper.calculatorPatchToCalculator(calculatorPatchDto);
        Calculator updateCalculator = calculatorService.updateResult(calculator);
        return new ResponseEntity<>(calculatorMapper.calculatorToPatchResponse(updateCalculator), HttpStatus.OK);

    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteResult(@PathVariable("calculator-id") @Positive long calculatorId){
        calculatorService.deleteResult(calculatorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
