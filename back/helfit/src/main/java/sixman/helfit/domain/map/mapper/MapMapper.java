package sixman.helfit.domain.map.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sixman.helfit.domain.map.dto.MapDto;
import sixman.helfit.domain.map.entity.Map;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapMapper {
    Map mapPostToMap(MapDto.Post requestBody);
    Map mapPatchToMap(MapDto.Patch requestBody);
    MapDto.Response mapToResponse(Map map);
    MapDto.PostResponse mapToPostResponse(Map map);
}
