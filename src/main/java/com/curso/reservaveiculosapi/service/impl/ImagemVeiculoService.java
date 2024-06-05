package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.imagem.ImagemRequest;
import com.curso.reservaveiculosapi.dto.request.imagem.ImagemUpdateRequest;
import com.curso.reservaveiculosapi.dto.response.imagem.ImagemResponse;
import com.curso.reservaveiculosapi.entity.ImagemVeiculoEntity;
import com.curso.reservaveiculosapi.entity.VeiculoEntity;
import com.curso.reservaveiculosapi.exceptions.custom.ImagemAlreadyRegisteredException;
import com.curso.reservaveiculosapi.exceptions.custom.ImagemVeiculoIsFullException;
import com.curso.reservaveiculosapi.exceptions.custom.ResourceNotFoundException;
import com.curso.reservaveiculosapi.repository.ImagemVeiculoRepository;
import com.curso.reservaveiculosapi.repository.VeiculoRepository;
import com.curso.reservaveiculosapi.service.IImagemVeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImagemVeiculoService implements IImagemVeiculoService {

    private final ImagemVeiculoRepository imagemVeiculoRepository;
    private final VeiculoRepository veiculoRepository;

    @Override
    @Transactional
    public ImagemResponse register(Long veiculoId, ImagemRequest request) {

        VeiculoEntity veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com o id: " + veiculoId));

        if (veiculo.getImagens().size() >= 6) {
            throw new ImagemVeiculoIsFullException("Veículo atingiu o máximo de imagens cadastradas");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(request.bytes());

        ImagemVeiculoEntity newImagem = ImagemVeiculoEntity.builder()
                .nome(request.nome())
                .extensao(request.extensao())
                .veiculo(veiculo)
                .bytes(decodedBytes)
                .build();

        veiculo.getImagens().add(newImagem);
        var veiculoSave = veiculoRepository.save(veiculo);

        var imagemSaved = veiculoSave.getImagens().getLast();

        return ImagemResponse.toResponse(imagemSaved);
    }

    @Override
    @Transactional
    public ImagemResponse update(Long veiculoId, ImagemUpdateRequest request) {
        VeiculoEntity veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com o id: " + veiculoId));

        ImagemVeiculoEntity imagemToUpdate;
        if (request.imagemId() != null) {
            imagemToUpdate = updateExistingImage(veiculo, request);
        } else {
            imagemToUpdate = addNewImage(veiculo, request);
        }

        return ImagemResponse.toResponse(imagemToUpdate);
    }

    private ImagemVeiculoEntity updateExistingImage(VeiculoEntity veiculo, ImagemUpdateRequest request) {
        ImagemVeiculoEntity imagemToUpdate = veiculo.getImagens().stream()
                .filter(imagem -> imagem.getId().equals(request.imagemId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));

        imagemToUpdate.setNome(request.nome());
        imagemToUpdate.setExtensao(request.extensao());
//        imagemToUpdate.setBytes(request.bytes());

        veiculoRepository.save(veiculo);

        return imagemToUpdate;
    }

    private ImagemVeiculoEntity addNewImage(VeiculoEntity veiculo, ImagemUpdateRequest request) {

        if (veiculo.getImagens().size() >= 6) {
            throw new ImagemVeiculoIsFullException("Veículo atingiu o máximo de imagens cadastradas");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(request.bytes());

        if (veiculo.getImagens().stream().anyMatch(imagem -> Arrays.equals(imagem.getBytes(), decodedBytes))) {
            throw new ImagemAlreadyRegisteredException("Imagem já cadastrada para esse veículo");
        }

        ImagemVeiculoEntity newImagem = ImagemVeiculoEntity.builder()
                .nome(request.nome())
                .extensao(request.extensao())
                .veiculo(veiculo)
                .bytes(decodedBytes)
                .build();

        veiculo.getImagens().add(newImagem);
        var veiculoSave = veiculoRepository.save(veiculo);

        var imagemSaved = veiculoSave.getImagens().getLast();

        return imagemSaved;
    }

    @Override
    public void delete(Long imagemVeiculoId) {
        if (!imagemVeiculoRepository.existsById(imagemVeiculoId)) {
            throw new ResourceNotFoundException("Imagem não encontrada com o id: " + imagemVeiculoId);
        }

        imagemVeiculoRepository.deleteById(imagemVeiculoId);
    }
}
