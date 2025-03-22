package com.spring.minipot.setup.controllers;

import com.spring.minipot.setup.DTO.*;
import com.spring.minipot.setup.models.User;
import com.spring.minipot.setup.responses.LoginResponse;
import com.spring.minipot.setup.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDTO request) {
        User user = service.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginUserDTO userDTO) {
        LoginResponse user = service.login(userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verify-account/{token}")
    public ResponseEntity<String> verify(@PathVariable String token) {
        service.verify(token);
        return ResponseEntity.ok("Account verificato con successo");
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<?> sendResetEmail(@Valid @RequestBody RequestResetPasswordDTO requestReset) {
        service.sendResetEmail(requestReset);
        return ResponseEntity.ok("Email di reset inviata con successo");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDTO requestReset) {
        service.resetPassword(requestReset);
        return ResponseEntity.ok("Password aggiornata con successo");
    }
}