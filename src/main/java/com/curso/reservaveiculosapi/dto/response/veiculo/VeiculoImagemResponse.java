package com.curso.reservaveiculosapi.dto.response.veiculo;

import com.curso.reservaveiculosapi.entity.ImagemVeiculoEntity;

import java.io.Serializable;
import java.util.Base64;

public record VeiculoImagemResponse(
        Long id,
        String nome,
        String extensao,
        String bytes,
        Long veiculoId
) implements Serializable {
    public static VeiculoImagemResponse toResponse(ImagemVeiculoEntity imagemEntity) {
        String imagemBase64 = "data:image/" + imagemEntity.getExtensao() + ";base64," + Base64.getEncoder().encodeToString(imagemEntity.getBytes());
        return new VeiculoImagemResponse(
                imagemEntity.getId(),
                imagemEntity.getNome(),
                imagemEntity.getExtensao(),
                imagemBase64,
                imagemEntity.getVeiculo().getId()
        );
    }
}