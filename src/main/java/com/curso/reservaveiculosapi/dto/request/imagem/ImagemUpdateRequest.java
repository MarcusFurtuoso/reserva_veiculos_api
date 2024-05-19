package com.curso.reservaveiculosapi.dto.request.imagem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImagemUpdateRequest (
        Long imagemId,
        @NotNull @NotBlank String nome,
        @NotNull byte[] bytes,
        @NotNull @NotBlank String extensao
) {
}
