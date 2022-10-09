package ru.lenivtsev.model.mapper;

import org.mapstruct.*;
import ru.lenivtsev.model.User;
import ru.lenivtsev.model.dto.UserDto;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserDtoMapper {

    @Mapping(target = "password", ignore = true)
    UserDto map(User user);

    User map(UserDto userDto);
}
