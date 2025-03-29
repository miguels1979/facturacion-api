package com.kosmostecnologia.facturador.domain.repository;

import bo.gob.impuestos.siat.ParametricaLeyendasDto;
import com.kosmostecnologia.facturador.persistence.view.LeyendaView;

import java.util.Optional;

public interface ILeyendaRepository {
    void save(ParametricaLeyendasDto parametricaLeyendasDto);
    void deleteAll();
    Optional<LeyendaView> obtenerLeyenda(String codigoActividad);
}
