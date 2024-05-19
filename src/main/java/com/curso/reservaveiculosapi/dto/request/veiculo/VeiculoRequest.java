package com.curso.reservaveiculosapi.dto.request.veiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoRequest(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String marca,
        @NotNull @NotBlank String tipo) {
}
