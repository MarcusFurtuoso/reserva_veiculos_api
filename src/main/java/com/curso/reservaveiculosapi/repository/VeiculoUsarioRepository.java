package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.entity.VeiculoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoUsarioRepository extends JpaRepository<VeiculoUsuarioEntity, Long>{
    Optional<VeiculoUsuarioEntity> findByUsuarioIdAndVeiculoId(Long usuarioId, Long veiculoId);

    List<VeiculoUsuarioEntity> findAllByUsuarioId(Long usuarioId);
}
