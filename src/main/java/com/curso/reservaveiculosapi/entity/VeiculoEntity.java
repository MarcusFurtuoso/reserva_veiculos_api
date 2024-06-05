package com.curso.reservaveiculosapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vei_veiculo", schema = "public")
public class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vei_nr_id")
    private Long id;

    @Column(name = "vei_tx_nome")
    private String nome;

    @Column(name = "vei_tx_marca")
    private String marca;

    @Column(name = "vei_tx_tipo")
    private String tipo;

    @Column(name = "vei_dc_preco")
    private BigDecimal preco;

    @Column(name = "vei_tx_descricao")
    @Length(max = 300)
    private String descricao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagemVeiculoEntity> imagens;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VeiculoUsuarioEntity> usuarios;

}
