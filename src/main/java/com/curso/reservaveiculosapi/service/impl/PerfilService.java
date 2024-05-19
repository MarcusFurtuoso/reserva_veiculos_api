package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.perfil.PerfilRequest;
import com.curso.reservaveiculosapi.dto.response.perfil.PerfilResponse;
import com.curso.reservaveiculosapi.dto.response.perfil.PerfilUsuarioAllResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioResponse;
import com.curso.reservaveiculosapi.entity.PerfilEntity;
import com.curso.reservaveiculosapi.exceptions.custom.PerfilAlreadyRegisteredException;
import com.curso.reservaveiculosapi.exceptions.custom.ResourceNotFoundException;
import com.curso.reservaveiculosapi.repository.PerfilRepository;
import com.curso.reservaveiculosapi.service.IPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService implements IPerfilService {

    private final PerfilRepository perfilRepository;

    @Override
    public List<PerfilResponse> listAll() {
        return perfilRepository.findAll().stream().map(PerfilResponse::toResponse).toList();
    }

    @Override
    @Transactional
    public PerfilUsuarioAllResponse listAllUsuariosByPerfilNome(String nome) {
        PerfilEntity perfil = perfilRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o nome: " + nome));

        List<UsuarioResponse> usuarios = perfil.getUsuarios().stream()
                .map(usuario -> new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin()))
                .toList();

        return PerfilUsuarioAllResponse.toResponse(perfil.getNome(), usuarios);
    }

    @Override
    public PerfilResponse update(Long perfilId, PerfilRequest perfil) {
        PerfilEntity perfilEntity = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o id: " + perfilId));

        perfilEntity.setNome(perfil.nome().toUpperCase());

        PerfilEntity perfilSave = perfilRepository.save(perfilEntity);

        return PerfilResponse.toResponse(perfilSave);
    }

    @Override
    public PerfilResponse register(PerfilRequest request) {

        var perfilExist = perfilRepository.findByNomeIgnoreCase(request.nome());

        if (perfilExist.isPresent()) {
            throw new PerfilAlreadyRegisteredException("Perfil já cadastrado com o nome: " + request.nome().toUpperCase());
        }

        PerfilEntity newPerfil = PerfilEntity.builder()
                .nome(request.nome().toUpperCase())
                .build();

        PerfilEntity perfilSave = this.perfilRepository.save(newPerfil);
        return PerfilResponse.toResponse(perfilSave);
    }

    @Override
    public void delete(Long perfilId) {
        if (!perfilRepository.existsById(perfilId)) {
            throw new ResourceNotFoundException("Perfil não encontrado com o id: " + perfilId);
        }

        perfilRepository.deleteById(perfilId);
    }

}
