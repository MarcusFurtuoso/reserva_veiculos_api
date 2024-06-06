package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.perfil.PerfilRequest;
import com.curso.reservaveiculosapi.dto.response.perfil.PerfilResponse;
import com.curso.reservaveiculosapi.dto.response.perfil.PerfilUsuarioAllResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPerfilService {

    List<PerfilResponse> listAll();

    Page<PerfilResponse> listAllPageable(Pageable pageable);

    PerfilUsuarioAllResponse listAllUsuariosByPerfilNome(String nome);

    PerfilResponse update(Long id, PerfilRequest perfil);

    PerfilResponse register(PerfilRequest perfil);

    void delete(Long id);
}
