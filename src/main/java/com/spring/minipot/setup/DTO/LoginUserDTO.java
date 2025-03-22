package com.spring.minipot.setup.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginUserDTO {

    @NotBlank(message = "Inserisci un account valido")
    @NotNull
    @NotEmpty
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    @NotNull
    @NotEmpty
    private String password;
}

