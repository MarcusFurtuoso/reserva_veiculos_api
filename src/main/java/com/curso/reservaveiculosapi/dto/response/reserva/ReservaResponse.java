package com.curso.reservaveiculosapi.dto.response.reserva;

import com.curso.reservaveiculosapi.entity.VeiculoUsuarioEntity;

import java.io.Serializable;
import java.sql.Date;

public record ReservaResponse(
        Long id,
        String usuario,
        Long veiculoId,
        Date dataInicial,
        Date dataFinal
) implements Serializable {
    public static ReservaResponse toResponse(VeiculoUsuarioEntity veiculoUsuarioEntity) {
        return new ReservaResponse(
                veiculoUsuarioEntity.getId(),
                veiculoUsuarioEntity.getUsuario().getLogin(),
                veiculoUsuarioEntity.getVeiculo().getId(),
                veiculoUsuarioEntity.getDateInicial(),
                veiculoUsuarioEntity.getDateFinal()
        );
    }
}
