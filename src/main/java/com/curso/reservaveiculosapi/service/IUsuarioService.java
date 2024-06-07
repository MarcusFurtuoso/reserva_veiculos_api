package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.usuario.UsuarioToPerfilRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.UsuarioUpdateRequest;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioToPerfilResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioResponse;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioResponse> listAll();

    List<VeiculoResponse> listAllVeiculosByUsuario(Long usuarioId);

    UsuarioToPerfilResponse addUsuarioToPerfil(UsuarioToPerfilRequest request);

        UsuarioResponse update(Long usuarioId, UsuarioUpdateRequest request);

    UsuarioResponse  findById(Long usuarioId);

    UsuarioResponse  findByLogin(String login);

    void delete(Long usuarioId);

}
