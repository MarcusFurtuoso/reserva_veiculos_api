package com.curso.reservaveiculosapi.dto.response.imagem;

import com.curso.reservaveiculosapi.entity.ImagemVeiculoEntity;

import java.io.Serializable;

public record ImagemResponse(
        Long id,
        String nome,
        String extensao,
        Long veiculoId
) implements Serializable {
    public static ImagemResponse toResponse(ImagemVeiculoEntity imagemVeiculoEntity) {
        return new ImagemResponse(
                imagemVeiculoEntity.getId(),
                imagemVeiculoEntity.getNome(),
                imagemVeiculoEntity.getExtensao(),
                imagemVeiculoEntity.getVeiculo().getId()
        );
    }
}
