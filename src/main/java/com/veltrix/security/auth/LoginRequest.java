package com.veltrix.security.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @JsonAlias({"correo", "email"})
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public String getCorreo() {
        return email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }
}