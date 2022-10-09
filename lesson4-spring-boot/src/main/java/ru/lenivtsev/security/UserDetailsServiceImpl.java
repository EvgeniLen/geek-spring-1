package ru.lenivtsev.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.lenivtsev.model.dto.UserDto;
import ru.lenivtsev.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService, IAuthenticationFacade {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserByUsername(username);
    }

    @Override
    public Optional<UserDto> getAuthentication() {
        return userService.findUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
