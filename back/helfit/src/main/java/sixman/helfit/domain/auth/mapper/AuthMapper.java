package sixman.helfit.domain.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.auth.dto.AuthDto;
import sixman.helfit.domain.auth.entity.Auth;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    AuthDto.Response authToAuthDtoResponse(Auth auth);
}
