package com.spring.ecommerce.setup.repositories;

import com.spring.ecommerce.setup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    //Cerco user per username
    Optional<User> findByUsername(String username);
    //Cerco user per email
    Optional<User> findByEmail(String email);
    //Se esiste la mail
    boolean existsByEmail(String email);
}
