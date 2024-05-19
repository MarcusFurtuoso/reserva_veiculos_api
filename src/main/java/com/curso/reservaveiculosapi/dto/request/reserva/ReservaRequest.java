package com.curso.reservaveiculosapi.dto.request.reserva;

import jakarta.validation.constraints.NotNull;

public record ReservaRequest(
        @NotNull Long usuarioId,
        @NotNull Long veiculoId
) {
}
