package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.usuario.UsuarioToPerfilRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioToPerfilResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioResponse;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;
import com.curso.reservaveiculosapi.entity.PerfilEntity;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import com.curso.reservaveiculosapi.entity.VeiculoUsuarioEntity;
import com.curso.reservaveiculosapi.exceptions.custom.ResourceNotFoundException;
import com.curso.reservaveiculosapi.exceptions.custom.UsuarioAlreadyRegisteredWithPerfilException;
import com.curso.reservaveiculosapi.repository.PerfilRepository;
import com.curso.reservaveiculosapi.repository.UsuarioRepository;
import com.curso.reservaveiculosapi.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    @Override
    public List<UsuarioResponse> listAll() {
        return usuarioRepository.findAll().stream().map(UsuarioResponse::toResponse).toList();
    }

    @Override
    @Transactional
    public List<VeiculoResponse> listAllVeiculosByUsuario(Long usuarioId) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + usuarioId));

        return usuario.getVeiculos().stream()
                .map(VeiculoUsuarioEntity::getVeiculo)
                .map(VeiculoResponse::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioToPerfilResponse addUsuarioToPerfil(UsuarioToPerfilRequest request) {
        PerfilEntity perfil = perfilRepository.findByNomeIgnoreCase(request.perfil())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com nome: " + request.perfil()));

        if(perfil.getUsuarios().stream().anyMatch(usuario -> usuario.getLogin().equals(request.login())))
            throw new UsuarioAlreadyRegisteredWithPerfilException("Usuário já possui o perfil: " + request.perfil().toUpperCase());

        return usuarioRepository.findUsuarioEntityByLogin(request.login())
                .map(usuario -> {
                    usuario.getPerfis().add(perfil);
                    usuarioRepository.save(usuario);
                    return UsuarioToPerfilResponse.toResponse(usuario, perfil);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o login: " + request.login()));
    }

    @Override
    public UsuarioResponse update(Long usuarioId, RegisterRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + usuarioId));

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.senha());

        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuario.setSenha(encryptedPassword);

        UsuarioEntity usuarioSave = usuarioRepository.save(usuario);
        return UsuarioResponse.toResponse(usuarioSave);
    }

    @Override
    public UsuarioResponse findById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioResponse::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + usuarioId));
    }

    @Override
    public UsuarioResponse findByLogin(String login) {
        return usuarioRepository.findUsuarioEntityByLogin(login)
                .map(UsuarioResponse::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o login: " + login));
    }

    @Override
    public void delete(Long usuarioId) {
        if(!usuarioRepository.existsById(usuarioId))
            throw new ResourceNotFoundException("Usuário não encontrado com o id: " + usuarioId);

        usuarioRepository.deleteById(usuarioId);
    }
}
