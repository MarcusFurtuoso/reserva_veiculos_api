package com.curso.reservaveiculosapi.dto.request.reserva;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public record  ReservaRequest(
        @NotNull Long usuarioId,
        @NotNull Long veiculoId,
        @NotNull Date dataInicial,
        @NotNull Date dataFinal
) {
}
