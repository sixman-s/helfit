package sixman.helfit.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoSignupToUser(UserDto.Signup requestBody);
    User userDtoPatchToUser(UserDto.Patch requestBody);

    UserDto.Response userToUserDtoResponse(User user);
}
