package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.reserva.ReservaRequest;
import com.curso.reservaveiculosapi.dto.response.reserva.ReservaResponse;
import com.curso.reservaveiculosapi.dto.response.reserva.ReserveUsuarioListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReservaService {

    List<ReservaResponse> listAll();

    List<ReserveUsuarioListResponse> listAllByUsuarioId(Long usuarioId);

    Page<ReserveUsuarioListResponse> listAllByUsuarioId(Pageable pageable, Long usuarioId);

    ReserveUsuarioListResponse findById(Long id);

    ReservaResponse registerReserva(ReservaRequest request);

}
