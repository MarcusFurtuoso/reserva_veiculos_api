package com.curso.reservaveiculosapi.dto.request.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotNull @NotBlank String login,
        @NotNull @NotBlank String senha
) {
}
