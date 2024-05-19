package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoRequest;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;
import com.curso.reservaveiculosapi.entity.VeiculoEntity;
import com.curso.reservaveiculosapi.exceptions.custom.ResourceNotFoundException;
import com.curso.reservaveiculosapi.repository.VeiculoRepository;
import com.curso.reservaveiculosapi.service.IVeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
