package com.curso.reservaveiculosapi.dto.request.imagem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImagemRequest(
        @NotNull @NotBlank String nome,
        @NotNull String bytes,
        @NotNull @NotBlank String extensao
) {
}
