package com.kosmostecnologia.facturador.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreRazonSocial;
    private Integer codigoTipoDocumentoIdentidad;
    private String numeroDocumento;
    private String complemento;
    private String codigoCliente;

    @Column(unique = true)
    private String email;

}
