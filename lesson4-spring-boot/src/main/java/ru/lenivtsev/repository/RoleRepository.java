package ru.lenivtsev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lenivtsev.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
