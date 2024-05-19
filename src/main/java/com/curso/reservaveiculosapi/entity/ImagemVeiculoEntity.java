package com.curso.reservaveiculosapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imv_imagem_veiculo", schema = "public")
public class ImagemVeiculoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "imv_nr_id")
        private Long id;

        @Column(name = "imv_tx_nome")
        private String nome;

        @Column(name = "imv_bt_bytes")
        private byte[] bytes;

        @Column(name = "imv_tx_extensao")
        private String extensao;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vei_nr_id", referencedColumnName = "vei_nr_id", nullable = false)
        private VeiculoEntity veiculo;
}
