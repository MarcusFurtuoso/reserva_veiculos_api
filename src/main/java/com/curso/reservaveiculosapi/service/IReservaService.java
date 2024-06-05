package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.reserva.ReservaRequest;
import com.curso.reservaveiculosapi.dto.response.reserva.ReservaResponse;
import com.curso.reservaveiculosapi.dto.response.reserva.ReserveUsuarioListResponse;

import java.util.List;

public interface IReservaService {

    List<ReservaResponse> listAll();

    List<ReserveUsuarioListResponse> listAllByUsuarioId(Long usuarioId);

    ReserveUsuarioListResponse findById(Long id);

    ReservaResponse registerReserva(ReservaRequest request);

}
