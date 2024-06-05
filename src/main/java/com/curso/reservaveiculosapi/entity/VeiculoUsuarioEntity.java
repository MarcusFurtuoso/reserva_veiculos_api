package com.curso.reservaveiculosapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vus_veiculo_usuario", schema = "public")
public class VeiculoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vus_nr_id")
    private Long id;

    @Column(name = "vus_dt_date_inicial")
    private Date dateInicial;

    @Column(name = "vus_dt_date_final")
    private Date dateFinal;

    @ManyToOne
    @JoinColumn(name = "vei_nr_id", referencedColumnName = "vei_nr_id")
    private VeiculoEntity veiculo;

    @ManyToOne
    @JoinColumn(name = "usu_nr_id", referencedColumnName = "usu_nr_id")
    private UsuarioEntity usuario;
}