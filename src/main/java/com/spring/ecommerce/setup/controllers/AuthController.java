package com.spring.ecommerce.setup.controllers;

import com.spring.ecommerce.setup.DTO.LoginUserDTO;
import com.spring.ecommerce.setup.DTO.RegisterUserDTO;
import com.spring.ecommerce.setup.models.User;
import com.spring.ecommerce.setup.repositories.UserRepository;
import com.spring.ecommerce.setup.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDTO request) {
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDTO request) {
        String token = authService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}

