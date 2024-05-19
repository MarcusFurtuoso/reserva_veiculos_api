package com.curso.reservaveiculosapi.dto.response.veiculo;

import com.curso.reservaveiculosapi.entity.VeiculoEntity;

import java.io.Serializable;

public record VeiculoResponse(
        Long id,
        String nome,
        String marca,
        String tipo
) implements Serializable {
    public static VeiculoResponse toResponse(VeiculoEntity veiculoEntity) {
        return new VeiculoResponse(
                veiculoEntity.getId(),
                veiculoEntity.getNome(),
                veiculoEntity.getMarca(),
                veiculoEntity.getTipo()
        );
    }
}
