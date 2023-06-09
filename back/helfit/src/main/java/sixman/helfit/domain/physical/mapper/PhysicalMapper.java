package sixman.helfit.domain.physical.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.physical.dto.PhysicalDto;
import sixman.helfit.domain.physical.entity.Physical;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhysicalMapper {
    PhysicalMapper INSTANCE = Mappers.getMapper(PhysicalMapper.class);

    Physical physicalDtoPostToPhysical(PhysicalDto.Post requestBody);

    Physical physicalDtoPatchToPhysical(PhysicalDto.Patch requestBody);

    PhysicalDto.Response physicalToPhysicalDtoResponse(Physical physical);

    List<PhysicalDto.Response> physicalToPhysicalDtoResponseList(List<Physical> physical);
}
