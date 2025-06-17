package it.epicode.u5w3d1pratica.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "Il nome non può essere vuoto")
    private String nome;
    @NotEmpty(message = "Il cognome non può essere vuoto")
    private String cognome;
    @NotEmpty(message = "L'email non può essere vuota")
    @Email(message = "L'email deve essere ben strutturata")
    private String email;
    @NotEmpty(message = "La password non può essere vuota")
    private String password;
    private String username;

}
