package sixman.helfit.domain.calculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.calculator.dto.CalculatorDto;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.helper.CalculatorHelper;
import sixman.helfit.domain.calculator.mapper.CalculatorMapper;
import sixman.helfit.domain.calculator.service.CalculatorService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.security.entity.UserPrincipal;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/calculate")
public class CalculatorController {
    private static final String DEFAULT_URI = "/api/v1/calculate";
    private final CalculatorService calculatorService;
    private final CalculatorMapper calculatorMapper;

    public CalculatorController(CalculatorService calculatorService, CalculatorMapper calculatorMapper) {
        this.calculatorService = calculatorService;
        this.calculatorMapper = calculatorMapper;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{user-id}")
    public ResponseEntity postResult(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CalculatorDto.Post requestBody) {

        Calculator calculator = calculatorService.createResult(calculatorMapper.calculatorPostToCalculator(requestBody));
        double bmr;
        if (userPrincipal.getUser().getGender().equals(User.Gender.MALE)) {
            bmr = CalculatorHelper.calculateBMR_Male(userPrincipal.getUser());
        } else {
            bmr = CalculatorHelper.calculateBMR_Female(userPrincipal.getUser());
        }
        double result = CalculatorHelper.calculateResult(bmr, requestBody.getActivityLevel(), requestBody.getGoal());
        calculator.setResult(result);
        return new ResponseEntity<>(calculatorMapper.calculatorToPostResponse(calculator), HttpStatus.CREATED);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{user-id}")
    public ResponseEntity<CalculatorDto.GetResponse> getResult(@PathVariable("calculator-id") @Positive long calculatorId) {
        Calculator calculator = new Calculator();
        Calculator findResult = calculatorService.findResult(calculator.getCalculatorId());
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
