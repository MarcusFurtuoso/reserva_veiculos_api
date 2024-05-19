package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.reserva.ReservaRequest;
import com.curso.reservaveiculosapi.dto.response.reserva.ReservaResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
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
    public List<ReservaResponse> listAllByUsuarioId(Long usuarioId) {
        if(!usuarioRepository.existsById(usuarioId))
            throw new ResourceNotFoundException("Usuário não encontrado com o id: " + usuarioId);

        return veiculoUsarioRepository.findAllByUsuarioId(usuarioId).stream()
                .map(ReservaResponse::toResponse)
                .toList();
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
                .date(Date.valueOf(LocalDate.now()))
                .build();

        usuario.getVeiculos().add(newReserva);
        veiculo.getUsuarios().add(newReserva);

        usuarioRepository.save(usuario);
        VeiculoEntity save = veiculoRepository.save(veiculo);

        var reservaSave = save.getUsuarios().getLast();

        return ReservaResponse.toResponse(reservaSave);
    }
}
