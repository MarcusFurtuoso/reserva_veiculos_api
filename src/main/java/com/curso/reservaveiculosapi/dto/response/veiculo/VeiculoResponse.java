package com.curso.reservaveiculosapi.dto.response.veiculo;

import com.curso.reservaveiculosapi.entity.VeiculoEntity;

import java.io.Serializable;
import java.math.BigDecimal;

public record VeiculoResponse(
        Long id,
        String nome,
        String marca,
        String tipo,
        String descricao,
        BigDecimal preco
) implements Serializable {
    public static VeiculoResponse toResponse(VeiculoEntity veiculoEntity) {
        return new VeiculoResponse(
                veiculoEntity.getId(),
                veiculoEntity.getNome(),
                veiculoEntity.getMarca(),
                veiculoEntity.getTipo(),
                veiculoEntity.getDescricao(),
                veiculoEntity.getPreco()
        );
    }
}
