package com.kosmostecnologia.facturador.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "actividad_documento_sector")
public class ActividadDocumentoSectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String codigoActividad;

    private Integer codigoDocumentoSector;

    @Column(length = 10)
    private String tipoDocumentoSector;

}
