package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoFilter;
import com.curso.reservaveiculosapi.entity.VeiculoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {

    @Query(nativeQuery = true, value = """
             select * from vei_veiculo vei
            where (:#{#veiculoFilter == null || #veiculoFilter.tipo() == null} or UPPER(vei_tx_tipo) = UPPER(:#{#veiculoFilter.tipo()}))
            """)
    Page<VeiculoEntity> findVehiclesByType(Pageable pageable, VeiculoFilter veiculoFilter);
}
