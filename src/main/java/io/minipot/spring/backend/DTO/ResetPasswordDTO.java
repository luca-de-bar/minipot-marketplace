package io.minipot.spring.backend.DTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    @NotBlank(message = "Il token non può essere vuoto. Inserisci quello inviato via mail")
    @NotNull
    @NotEmpty
    private String token;

    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&*]).*$", message = "La password deve contenere almeno una lettera maiuscola e un simbolo")
    private String newPassword;

    private String confirmPassword;

    public ResetPasswordDTO (){}
}