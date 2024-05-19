package com.curso.reservaveiculosapi.dto.request.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerfilRequest(
        @NotNull @NotBlank String nome
) {
}
