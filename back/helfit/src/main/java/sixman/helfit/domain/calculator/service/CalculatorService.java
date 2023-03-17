package sixman.helfit.domain.calculator.service;

import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.helper.CalculatorHelper;
import sixman.helfit.domain.calculator.repository.CalculatorRepository;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.utils.CustomBeanUtil;

import java.util.List;
import java.util.Optional;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final CustomBeanUtil<Calculator> customBeanUtil;

    public CalculatorService(CalculatorRepository calculatorRepository, UserService userService, CustomBeanUtil<Calculator> customBeanUtil) {
        this.calculatorRepository = calculatorRepository;
        this.customBeanUtil = customBeanUtil;
    }

    public Calculator createResult(Calculator calculator, User user, Physical physical) {
        if (physical.getWeight() == null || physical.getHeight() == null || physical.getGender() == null) {
            throw new BusinessLogicException(ExceptionCode.CALCULATOR_NO_USER_INFO);
        }
        calculator.setResult(CalculatorHelper.calculateResultWithGender(calculator.getActivityLevel(),calculator.getGoal(), physical));
        calculator.setUser(user);

        return calculatorRepository.save(calculator);
    }

    public Calculator findUserResult(Long userId) {
        Optional<Calculator> optionalCalculator = calculatorRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
        if (optionalCalculator.isPresent()) {
            Calculator calculator = optionalCalculator.get();
            return calculator;
        } else  {
            throw new NotFoundException("해당 사용자의 계산 결과를 찾을 수 없습니다.");
        }
    }

    public Calculator updateResult(Calculator calculator, User user, Physical physical) {

        calculator.setResult(CalculatorHelper.calculateResultWithGender(calculator.getActivityLevel(),calculator.getGoal(), physical));
        calculator.setUser(user);

        return calculatorRepository.save(calculator);
    }
    public Calculator updateCalculator(Calculator requestCalculator, Calculator verifiedCalculator) {
        Calculator updatedCalculator = customBeanUtil.copyNonNullProperties(requestCalculator, verifiedCalculator);
        return calculatorRepository.save(updatedCalculator);
    }

    public void deleteResult(Long userId) {
        Calculator findResult = findVerifyCalculator(userId);
        calculatorRepository.delete(findResult);
    }

    private Calculator findVerifyCalculator(Long calculatorId) {
        Optional<Calculator> optionalCalculator =
                calculatorRepository.findById(calculatorId);
        return optionalCalculator.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CALCULATOR_NOT_FOUND));
    }
    public Calculator findVerifiedCalculatorWithUserId(Long calculatorId, Long userId) {
        Optional<Calculator> byCalculatorId = calculatorRepository.findByCalculatorId(calculatorId);

        Calculator calculator = byCalculatorId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));

        if (!calculator.getUser().getUserId().equals(userId))
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);

        return calculator;
    }
}
