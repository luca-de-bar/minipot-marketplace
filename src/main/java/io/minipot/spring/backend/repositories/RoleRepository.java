package io.minipot.spring.backend.repositories;

import io.minipot.spring.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
