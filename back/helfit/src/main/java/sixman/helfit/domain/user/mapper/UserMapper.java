package sixman.helfit.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", source = "id")
    User userDtoSignupToUser(UserDto.Signup requestBody);
    User userDtoLoginToUser(UserDto.Login requestBody);

    UserDto.Response userToUserDtoResponse(User user);
}
