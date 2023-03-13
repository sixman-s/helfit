package sixman.helfit.domain.calculator.service;

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
    public Calculator findResult(long calculatorId){
        Calculator calculator = new Calculator();
        calculator.setCalculatorId(calculatorId);
        return findVerifyCalculator(calculator.getCalculatorId());
    }
    public Calculator updateResult(Calculator calculator){
        Calculator findCalculator = findVerifyCalculator(calculator.getCalculatorId());
        Optional.ofNullable(calculator.getResult())
                .ifPresent(result -> findCalculator.setResult(result));
        return calculatorRepository.save(findCalculator);
    }
    public void deleteResult(long calculatorId){
        Calculator findResult = findVerifyCalculator(calculatorId);
        calculatorRepository.delete(findResult);
        }
    private Calculator findVerifyCalculator(long calculatorId){
        Optional<Calculator> optionalCalculator =
                calculatorRepository.findById(calculatorId);
        return optionalCalculator.orElseThrow(() ->
        new BusinessLogicException(ExceptionCode.CALCULATOR_NOT_FOUND));
    }
    public Calculator findUserResult(long userId){
        User findUser = userService.findUserByUserId(userId);
        return calculatorRepository.findByUser(findUser);
    }
}
