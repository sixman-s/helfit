package sixman.helfit.domain.calculator.service;

import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.stereotype.Service;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.repository.CalculatorRepository;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;

import java.util.Optional;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final UserService userService;

    public CalculatorService(CalculatorRepository calculatorRepository, UserService userService) {
        this.calculatorRepository = calculatorRepository;
        this.userService = userService;
    }

    public Calculator createResult(Calculator calculator){

        Calculator savedResult = calculatorRepository.save(calculator);
        return savedResult;
    }
//    public Calculator findResult(Long calculatorId){
//        Calculator calculator = new Calculator();
//        calculator.setCalculatorId(calculatorId);
//        return findVerifyCalculator(calculator.getCalculatorId());
//    }
    public Calculator updateResult(Long userId){
        Calculator findCalculator = findUserResult(userId);
        Optional.ofNullable(findCalculator.getResult())
                .ifPresent(result -> findCalculator.setResult(result));
        return calculatorRepository.save(findCalculator);
    }
    public void deleteResult(Long calculatorId){
        Calculator findResult = findVerifyCalculator(calculatorId);
        calculatorRepository.delete(findResult);
        }
    private Calculator findVerifyCalculator(Long calculatorId){
        Optional<Calculator> optionalCalculator =
                calculatorRepository.findById(calculatorId);
        return optionalCalculator.orElseThrow(() ->
        new BusinessLogicException(ExceptionCode.CALCULATOR_NOT_FOUND));
    }
//    public Calculator findUserResult(Long userId){
//        User findUser = userService.findVerifiedUserByUserId(userId);
//        return calculatorRepository.findByUser(findUser);
//    }
    public Calculator findUserResult(Long userId) {
    // userId에 해당하는 사용자의 최신 계산 결과를 조회.
        Optional<Calculator> optionalCalculator = calculatorRepository.findFirstByUserIdOrderByModifiedAtDesc(userId);
        if (optionalCalculator.isPresent()) {
            Calculator calculator = optionalCalculator.get();
            return calculator;
        } else {
            throw new NotFoundException("해당 사용자의 계산 결과를 찾을 수 없습니다.");
        }
    }

}
