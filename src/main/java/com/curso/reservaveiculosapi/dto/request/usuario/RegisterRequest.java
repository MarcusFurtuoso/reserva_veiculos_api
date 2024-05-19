package com.curso.reservaveiculosapi.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank @Email String login,
        @NotNull @NotBlank @Length(min = 5) String senha
) {

}
