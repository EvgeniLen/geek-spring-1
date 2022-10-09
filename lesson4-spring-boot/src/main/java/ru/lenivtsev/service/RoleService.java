package ru.lenivtsev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lenivtsev.model.Role;
import ru.lenivtsev.repository.RoleRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
