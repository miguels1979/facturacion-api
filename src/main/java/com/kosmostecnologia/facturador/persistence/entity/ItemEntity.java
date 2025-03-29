package com.kosmostecnologia.facturador.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String codigo;

    @Column(length = 500)
    private String descripcion;

    private Integer unidadMedida;

    private BigDecimal precioUnitario;

    private Integer codigoProductoSin;

}
