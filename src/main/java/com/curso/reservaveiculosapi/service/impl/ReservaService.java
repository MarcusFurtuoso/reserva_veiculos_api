package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.reserva.ReservaRequest;
import com.curso.reservaveiculosapi.dto.response.reserva.ReservaResponse;
import com.curso.reservaveiculosapi.dto.response.reserva.ReserveUsuarioListResponse;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import com.curso.reservaveiculosapi.entity.VeiculoEntity;
import com.curso.reservaveiculosapi.entity.VeiculoUsuarioEntity;
import com.curso.reservaveiculosapi.exceptions.custom.ReserveAlreadyRegisteredException;
import com.curso.reservaveiculosapi.exceptions.custom.ResourceNotFoundException;
import com.curso.reservaveiculosapi.repository.UsuarioRepository;
import com.curso.reservaveiculosapi.repository.VeiculoRepository;
import com.curso.reservaveiculosapi.repository.VeiculoUsarioRepository;
import com.curso.reservaveiculosapi.service.IReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService implements IReservaService {

    private final UsuarioRepository usuarioRepository;
    private final VeiculoRepository veiculoRepository;
    private final VeiculoUsarioRepository veiculoUsarioRepository;

    @Override
    public List<ReservaResponse> listAll() {
        return veiculoUsarioRepository.findAll().stream()
                .map(ReservaResponse::toResponse)
                .toList();
    }

    @Override
    public List<ReserveUsuarioListResponse> listAllByUsuarioId(Long usuarioId) {
        if(!usuarioRepository.existsById(usuarioId))
            throw new ResourceNotFoundException("Usuário não encontrado com o id: " + usuarioId);

        return veiculoUsarioRepository.findAllByUsuarioId(usuarioId).stream()
                .map(ReserveUsuarioListResponse::toResponse)
                .toList();
    }

    @Override
    public Page<ReserveUsuarioListResponse> listAllByUsuarioId(Pageable pageable, Long usuarioId) {
        return veiculoUsarioRepository.findAllByUsuarioId(pageable, usuarioId)
                .map(ReserveUsuarioListResponse::toResponse);
    }

    @Override
    public ReserveUsuarioListResponse findById(Long id) {
        return veiculoUsarioRepository.findById(id)
                .map(ReserveUsuarioListResponse::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com o id: " + id));
    }

    @Override
    @Transactional
    public ReservaResponse registerReserva(ReservaRequest request) {

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + request.usuarioId()));

        VeiculoEntity veiculo = veiculoRepository.findById(request.veiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com o id: " + request.veiculoId()));

        if (veiculoUsarioRepository.findByUsuarioIdAndVeiculoId(usuario.getId(), veiculo.getId()).isPresent()) {
            throw new ReserveAlreadyRegisteredException("Usuário já possui reserva para o veículo selecionado");
        }

        VeiculoUsuarioEntity newReserva = VeiculoUsuarioEntity.builder()
                .usuario(usuario)
                .veiculo(veiculo)
                .dateInicial(request.dataInicial())
                .dateFinal(request.dataFinal())
                .build();

        usuario.getVeiculos().add(newReserva);

        usuarioRepository.save(usuario);

        var reservaSave = usuario.getVeiculos().getLast();

        return ReservaResponse.toResponse(reservaSave);
    }
}
