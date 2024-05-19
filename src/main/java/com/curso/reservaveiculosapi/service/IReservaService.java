package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.reserva.ReservaRequest;
import com.curso.reservaveiculosapi.dto.response.reserva.ReservaResponse;

import java.util.List;

public interface IReservaService {

    List<ReservaResponse> listAll();

    List<ReservaResponse> listAllByUsuarioId(Long usuarioId);

    ReservaResponse registerReserva(ReservaRequest request);

}
