package com.curso.reservaveiculosapi.dto.response.reserva;

import com.curso.reservaveiculosapi.entity.VeiculoUsuarioEntity;

import java.io.Serializable;
import java.sql.Date;

public record ReserveUsuarioListResponse(
        Long id,
        String usuario,
        String veiculo,
        Date dataInicial,
        Date dataFinal,
        Long usuarioId,
        Long veiculoId
) implements Serializable {
    public static ReserveUsuarioListResponse toResponse(VeiculoUsuarioEntity veiculoUsuarioEntity) {
        return new ReserveUsuarioListResponse(
                veiculoUsuarioEntity.getId(),
                veiculoUsuarioEntity.getUsuario().getLogin(),
                veiculoUsuarioEntity.getVeiculo().getNome(),
                veiculoUsuarioEntity.getDateInicial(),
                veiculoUsuarioEntity.getDateFinal(),
                veiculoUsuarioEntity.getUsuario().getId(),
                veiculoUsuarioEntity.getVeiculo().getId()
        );
    }
}
