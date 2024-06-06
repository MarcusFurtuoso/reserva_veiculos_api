package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.entity.VeiculoUsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoUsarioRepository extends JpaRepository<VeiculoUsuarioEntity, Long>{
    Optional<VeiculoUsuarioEntity> findByUsuarioIdAndVeiculoId(Long usuarioId, Long veiculoId);

    List<VeiculoUsuarioEntity> findAllByUsuarioId(Long usuarioId);

    @Query(nativeQuery = true, value = "SELECT * FROM vus_veiculo_usuario WHERE usu_nr_id = :usuarioId",
            countQuery = "SELECT count(*) FROM vus_veiculo_usuario WHERE usu_nr_id = :usuarioId")
    Page<VeiculoUsuarioEntity> findAllByUsuarioId(Pageable pageable, Long usuarioId);
}
