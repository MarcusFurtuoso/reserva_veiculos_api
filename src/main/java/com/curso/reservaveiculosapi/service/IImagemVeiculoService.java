package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.imagem.ImagemRequest;
import com.curso.reservaveiculosapi.dto.request.imagem.ImagemUpdateRequest;
import com.curso.reservaveiculosapi.dto.response.imagem.ImagemResponse;

public interface IImagemVeiculoService {

    ImagemResponse register(Long veiculoId, ImagemRequest request);

    ImagemResponse update(Long veiculoId, ImagemUpdateRequest request);

    void delete(Long imagemVeiculoId);

}
