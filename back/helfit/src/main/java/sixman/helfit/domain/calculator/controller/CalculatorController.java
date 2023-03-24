package sixman.helfit.domain.calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.calculator.dto.CalculatorDto;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.mapper.CalculatorMapper;
import sixman.helfit.domain.calculator.service.CalculatorService;
import sixman.helfit.domain.physical.service.PhysicalService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
public class CalculatorController {
    private static final String DEFAULT_URL = "/api/v1/calculate";
    private final CalculatorService calculatorService;
    private final CalculatorMapper calculatorMapper;
    private final PhysicalService physicalService;
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/{user-id}")
//    public ResponseEntity<?> postResult(@AuthenticationPrincipal UserPrincipal userPrincipal,
//                                        @RequestBody CalculatorDto.Post requestBody) {
//        User user = userPrincipal.getUser();
//
//        Calculator calculator = calculatorService.createResult(
//                calculatorMapper.calculatorPostToCalculator(requestBody),
//                user,
//                physicalService.findPhysicalByUserId(user.getUserId())
//        );
//
//        URI uri = UriUtil.createUri(DEFAULT_URL, calculator.getCalculatorId());
//
//        CalculatorDto.PostResponse postResponse = calculatorMapper.calculatorToPostResponse(calculator);
//        postResponse.setUserId(userPrincipal.getUser().getUserId());
//        postResponse.setCalculatorId(calculator.getCalculatorId());
//
//        return ResponseEntity.created(uri).body(ApiResponse.ok("data", postResponse));
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{user-id}")
    public ResponseEntity<?> postResult(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @RequestBody CalculatorDto.Post requestBody) {
        User user = userPrincipal.getUser();

        Calculator calculator = calculatorService.createResult(
            calculatorMapper.calculatorPostToCalculator(requestBody),
            user,
            physicalService.findPhysicalByUserId(user.getUserId())
        );
        URI uri = UriUtil.createUri(DEFAULT_URL, user.getUserId());
        CalculatorDto.Response response = calculatorMapper.calculatorToResponse(calculator);
        //response.setCalculatorId(calculator.getCalculatorId());

        return ResponseEntity.created(uri).body(ApiResponse.created("data", response));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{user-id}")
    public ResponseEntity<?> getResult(@PathVariable("user-id") @Positive Long userId,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Calculator verifiedCalculatorWithUserId = calculatorService.findUserResult(userId);
        CalculatorDto.Response response = calculatorMapper.calculatorToResponse(verifiedCalculatorWithUserId);
        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{calculator-id}")
    public ResponseEntity<?> patchResult(@PathVariable("calculator-id") @Positive Long calculatorId,
                                         @Valid @RequestBody CalculatorDto.Patch requestBody,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        Calculator verifiedResult = calculatorService.findVerifiedCalculatorWithUserId(calculatorId, user.getUserId());

        Calculator calculator = calculatorService.updateCalculator(
            calculatorMapper.calculatorPatchToCalculator(requestBody),
            verifiedResult
        );
        Calculator afterResult = calculatorService.updateResult(
            calculator,
            user,
            physicalService.findPhysicalByUserId(user.getUserId())
        );

        CalculatorDto.Response response = calculatorMapper.calculatorToResponse(afterResult);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<?> deleteResult(@PathVariable("user-id") @Positive Long userId) {
        calculatorService.deleteResult(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
