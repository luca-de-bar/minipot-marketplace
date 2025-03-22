package com.spring.minipot.setup.repositories;

import com.spring.minipot.setup.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
