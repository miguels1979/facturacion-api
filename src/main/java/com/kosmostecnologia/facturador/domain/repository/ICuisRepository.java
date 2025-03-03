package com.kosmostecnologia.facturador.domain.repository;

import bo.gob.impuestos.siat.RespuestaCuis;
import com.kosmostecnologia.facturador.persistence.entity.CuisEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;

import java.util.Optional;

public interface ICuisRepository { // es bueno no depender de las implementaciones
    void save(RespuestaCuis respuestaCuis, PuntoVentaEntity puntoVenta);
    Optional<CuisEntity>findActual(PuntoVentaEntity puntoVenta);
}
