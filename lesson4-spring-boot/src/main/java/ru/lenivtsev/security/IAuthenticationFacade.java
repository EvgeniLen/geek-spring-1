package ru.lenivtsev.security;

import ru.lenivtsev.model.dto.UserDto;

import java.util.Optional;

public interface IAuthenticationFacade {
    Optional<UserDto> getAuthentication();
}
