package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
//        Set<Role> findByRole(User user);

}
