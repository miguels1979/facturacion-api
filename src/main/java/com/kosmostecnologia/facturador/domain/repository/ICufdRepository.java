package com.kosmostecnologia.facturador.domain.repository;

import bo.gob.impuestos.siat.RespuestaCufd;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;

public interface ICufdRepository {

    void save(RespuestaCufd respuestaCufd, PuntoVentaEntity puntoVenta);
}
