package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.dto.request.reserva.ReservaRequest;
import com.curso.reservaveiculosapi.dto.response.reserva.ReservaResponse;
import com.curso.reservaveiculosapi.dto.response.reserva.ReserveUsuarioListResponse;
import com.curso.reservaveiculosapi.service.impl.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
@Tag(name = "Reserva", description = "Serviços para gerenciar as reservas de veículos")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Lista todos as reservas")
    @ApiResponse(responseCode = "200",
            description = "Reservas listadas com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservaResponse.class)))
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservaResponse> listAll() {
        return reservaService.listAll();
    }

    @Operation(summary = "Lista todos as reservas de um usuário")
    @ApiResponse(responseCode = "200",
            description = "Reservas do usuário listadas com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservaResponse.class)))
    @GetMapping("/usuario")
    @ResponseStatus(HttpStatus.OK)
    public List<ReserveUsuarioListResponse> listAllByUsuarioId(@RequestParam Long usuarioId) {
        return reservaService.listAllByUsuarioId(usuarioId);
    }

    @Operation(summary = "Busca uma reserva pelo id")
    @ApiResponse(responseCode = "200",
            description = "Reserva encontrada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReserveUsuarioListResponse.class)))
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReserveUsuarioListResponse findById(@PathVariable Long id) {
        return reservaService.findById(id);
    }

    @Operation(summary = "Cadastra uma reserva de veículo")
    @ApiResponse(responseCode = "201",
            description = "Reserva cadastrada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservaResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponse registerReserva(@RequestBody @Valid ReservaRequest request) {
        return reservaService.registerReserva(request);
    }

}
