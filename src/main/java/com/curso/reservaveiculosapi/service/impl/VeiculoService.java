package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoFilter;
import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoRequest;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoImagemResponse;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;
import com.curso.reservaveiculosapi.entity.ImagemVeiculoEntity;
import com.curso.reservaveiculosapi.entity.VeiculoEntity;
import com.curso.reservaveiculosapi.exceptions.custom.ResourceNotFoundException;
import com.curso.reservaveiculosapi.repository.VeiculoRepository;
import com.curso.reservaveiculosapi.service.IVeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService implements IVeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Override
    public List<VeiculoResponse> listAll() {
        return veiculoRepository.findAll().stream().map(VeiculoResponse::toResponse).toList();
    }

    @Override
    public Page<VeiculoResponse> listAllPaginated(Pageable pageable, VeiculoFilter veiculoFilter) {
        return veiculoRepository.findVehiclesByType(pageable, veiculoFilter).map(VeiculoResponse::toResponse);
    }

    @Override
    public Page<VeiculoResponse> listAllBySearchPaginated(Pageable pageable, VeiculoFilter veiculoFilter) {
        return veiculoRepository.findAllSearch(pageable, veiculoFilter).map(VeiculoResponse::toResponse);
    }


    @Override
    public List<VeiculoResponse> listAllCars() {
        return veiculoRepository.findAll().stream().filter(
                veiculo -> veiculo.getTipo().equalsIgnoreCase("CARRO")
        ).map(VeiculoResponse::toResponse).toList(
        );
    }


    @Override
    public List<VeiculoResponse> listAllMotorcycles() {
        return veiculoRepository.findAll().stream().filter(
                veiculo -> veiculo.getTipo().equalsIgnoreCase("MOTO")
        ).map(VeiculoResponse::toResponse).toList();
    }


    @Override
    public List<VeiculoResponse> listAllTrucks() {
        return veiculoRepository.findAll().stream().filter(
                veiculo -> veiculo.getTipo().equalsIgnoreCase("CAMINHAO")
        ).map(VeiculoResponse::toResponse).toList();
    }


    @Override
    @Transactional
    public List<VeiculoImagemResponse> getImagens(Long veiculoId) {
        VeiculoEntity veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado com o id: " + veiculoId));

        List<ImagemVeiculoEntity> imagens = veiculo.getImagens();

        if (imagens.isEmpty()) {
            return List.of();
        }

        return imagens.stream()
                .map(VeiculoImagemResponse::toResponse)
                .toList();
    }

    @Override
    public VeiculoResponse findById(Long id) {
        return veiculoRepository.findById(id)
                .map(VeiculoResponse::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado com o id: " + id));
    }

    @Override
    public VeiculoResponse register(VeiculoRequest request) {
        VeiculoEntity newVeiculo = VeiculoEntity.builder()
                .nome(request.nome())
                .marca(request.marca())
                .tipo(request.tipo())
                .descricao(request.descricao())
                .preco(request.preco())
                .build();

        VeiculoEntity veiculoSave = this.veiculoRepository.save(newVeiculo);

        return VeiculoResponse.toResponse(veiculoSave);
    }

    @Override
    public VeiculoResponse update(Long veiculoId, VeiculoRequest veiculoRequest) {
        VeiculoEntity veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado com o id: " + veiculoId));

        veiculo.setNome(veiculoRequest.nome());
        veiculo.setMarca(veiculoRequest.marca());
        veiculo.setTipo(veiculoRequest.tipo());
        veiculo.setDescricao(veiculoRequest.descricao());
        veiculo.setPreco(veiculoRequest.preco());

        VeiculoEntity veiculoSave = veiculoRepository.save(veiculo);

        return VeiculoResponse.toResponse(veiculoSave);
    }

    @Override
    public void delete(Long veiculoId) {
        if (!veiculoRepository.existsById(veiculoId)) {
            throw new ResourceNotFoundException("Veiculo não encontrado com o id: " + veiculoId);
        }

        veiculoRepository.deleteById(veiculoId);
    }
}
