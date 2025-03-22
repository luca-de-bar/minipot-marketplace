package com.spring.minipot.setup.repositories;

import com.spring.minipot.setup.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(@NotEmpty @NotNull String email);
    Optional<User> findByPersonalToken(@NotNull @NotEmpty String personalToken);

}