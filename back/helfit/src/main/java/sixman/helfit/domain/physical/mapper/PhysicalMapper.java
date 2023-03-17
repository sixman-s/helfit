package sixman.helfit.domain.physical.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.physical.dto.PhysicalDto;
import sixman.helfit.domain.physical.entity.Physical;

@Mapper(componentModel = "spring")
public interface PhysicalMapper {
    PhysicalMapper INSTANCE = Mappers.getMapper(PhysicalMapper.class);

    Physical physicalDtoPostToPhysical(PhysicalDto.Post requestBody);

    Physical physicalDtoPatchToPhysical(PhysicalDto.Patch requestBody);

    PhysicalDto.Response physicalToPhysicalDtoResponse(Physical physical);
}
