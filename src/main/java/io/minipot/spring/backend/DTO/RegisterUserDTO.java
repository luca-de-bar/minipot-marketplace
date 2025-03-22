package io.minipot.spring.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "Il nome utente è obbligatorio")
    private String username;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Email non valida", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&*]).*$", message = "La password deve contenere almeno una lettera maiuscola e un simbolo")
    private String password;

}

