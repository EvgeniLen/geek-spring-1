package ru.lenivtsev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lenivtsev.model.User;
import ru.lenivtsev.model.dto.UserDto;
import ru.lenivtsev.model.mapper.UserDtoMapper;
import ru.lenivtsev.model.mapper.UserEncoderDtoMapper;
import ru.lenivtsev.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDtoMapper mapper;
    private final UserEncoderDtoMapper encoderDtoMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public Page<UserDto> findAllByFilter(String usernameFilter, String emailFilter, int page, int size, String sortField) {
        usernameFilter = usernameFilter == null || usernameFilter.isBlank() ? null : "%" + usernameFilter.trim() + "%";
        emailFilter = emailFilter == null || emailFilter.isBlank() ? null : "%" + emailFilter.trim() + "%";
        return userRepository.usersByFilter(usernameFilter, emailFilter, PageRequest.of(page, size, Sort.by(sortField)))
                .map(mapper::map);
    }

    public Optional<UserDto> findUserDtoById(Long id) {
        return userRepository.findById(id).map(mapper::map);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void save(UserDto dto) {
        userRepository.save(encoderDtoMapper.map(dto, encoder));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserDto> findUser(String name) {
        return userRepository.findByUsername(name).map(mapper::map);
    }

    public org.springframework.security.core.userdetails.User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                                .collect(Collectors.toList())
                )).orElseThrow(() -> new UsernameNotFoundException(username));

    }

}
