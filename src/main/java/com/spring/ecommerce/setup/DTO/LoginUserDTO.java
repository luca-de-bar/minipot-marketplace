package com.spring.ecommerce.setup.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginUserDTO {

    @NotBlank(message = "Il nome utente è obbligatorio")
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    private String password;
}

