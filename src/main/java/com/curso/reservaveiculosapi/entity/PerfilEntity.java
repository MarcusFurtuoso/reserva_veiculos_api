package com.curso.reservaveiculosapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "per_perfil", schema = "public")
public class PerfilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_nr_id")
    private Long id;

    @Column(name = "per_tx_nome", unique = true)
    private String nome;

    @ManyToMany(mappedBy = "perfis")
    private List<UsuarioEntity> usuarios;

}
