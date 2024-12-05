package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.DTO.LoginUserDTO;
import com.spring.ecommerce.setup.DTO.RegisterUserDTO;
import com.spring.ecommerce.setup.models.User;
import com.spring.ecommerce.setup.repositories.UserRepository;
import com.spring.ecommerce.setup.security.DatabaseUserDetails;
import com.spring.ecommerce.setup.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    // Dipendenze iniettate tramite costruttore
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(RegisterUserDTO request) {
        // Creiamo un nuovo utente e codifichiamo la password
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Aggiungi ruoli se necessario
        return userRepository.save(user);
    }

    public String authenticate(LoginUserDTO request) {
        // Autentichiamo l'utente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Recuperiamo i dettagli dell'utente dal database
        UserDetails userDetails = userRepository.findByUsername(request.getUsername())
                .map(DatabaseUserDetails::new)
                .orElseThrow();

        // Generiamo il token JWT
        return jwtService.generateToken(userDetails);
    }
}

