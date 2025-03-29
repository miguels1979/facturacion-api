package com.kosmostecnologia.facturador.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cufd")
public class CufdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String codigoControl;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaVigencia;
    private Boolean vigente;

    @ManyToOne
    @JoinColumn(name = "id_punto_venta", nullable = false)
    private PuntoVentaEntity puntoVenta;

}
