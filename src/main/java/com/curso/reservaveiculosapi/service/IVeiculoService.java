package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoRequest;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;

import java.util.List;

public interface IVeiculoService {

    List<VeiculoResponse> listAll();

    VeiculoResponse findById(Long id);

    VeiculoResponse register(VeiculoRequest veiculoRequest);

    VeiculoResponse update(Long veiculoId, VeiculoRequest veiculoRequest);

    void delete(Long id);

}
