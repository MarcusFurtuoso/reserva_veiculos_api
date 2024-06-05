package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoFilter;
import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoRequest;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoImagemResponse;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVeiculoService {

    List<VeiculoResponse> listAll();

    Page<VeiculoResponse> listAllPaginated(Pageable pageable, VeiculoFilter veiculoFilter);

    List<VeiculoResponse> listAllCars();

    List<VeiculoResponse> listAllMotorcycles();

    List<VeiculoResponse> listAllTrucks();

    List<VeiculoImagemResponse> getImagens(Long veiculoId);

    VeiculoResponse findById(Long id);

    VeiculoResponse register(VeiculoRequest veiculoRequest);

    VeiculoResponse update(Long veiculoId, VeiculoRequest veiculoRequest);

    void delete(Long id);

}
