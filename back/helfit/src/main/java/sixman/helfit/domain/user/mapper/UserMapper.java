package sixman.helfit.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.user.dto.UserDto;
import sixman.helfit.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoSignupToUser(UserDto.Signup requestBody);
    User userDtoPatchToUser(UserDto.Update requestBody);

    User userDtoPasswordToUser(UserDto.Password requestBody);

    UserDto.Response userToUserDtoResponse(User user);
}
