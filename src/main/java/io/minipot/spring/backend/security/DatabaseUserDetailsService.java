package io.minipot.spring.backend.security;

import io.minipot.spring.backend.models.User;
import io.minipot.spring.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);

        if(user.isPresent()){
            return new DatabaseUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Utente non trovato");
        }
    }
}
