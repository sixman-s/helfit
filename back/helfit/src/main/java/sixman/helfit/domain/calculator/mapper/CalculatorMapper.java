package sixman.helfit.domain.calculator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sixman.helfit.domain.calculator.dto.CalculatorDto;
import sixman.helfit.domain.calculator.entity.Calculator;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CalculatorMapper {
    Calculator calculatorPostToCalculator(CalculatorDto.Post requestBody);
    Calculator calculatorPatchToCalculator(CalculatorDto.Patch requestBody);
    CalculatorDto.PostResponse calculatorToPostResponse(Calculator calculator);
    CalculatorDto.PostResponse calculatorToPostResponseWithUserId(Calculator calculator, Long userId);
    CalculatorDto.GetResponse calculatorToGetResponse(Calculator calculator);
    CalculatorDto.PatchResponse calculatorToPatchResponse(Calculator calculator);
}
