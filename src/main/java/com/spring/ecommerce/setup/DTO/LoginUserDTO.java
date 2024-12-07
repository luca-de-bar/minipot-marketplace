package com.spring.ecommerce.setup.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginUserDTO {

    @NotBlank(message = "Inserisci un account valido")
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    private String password;
}

