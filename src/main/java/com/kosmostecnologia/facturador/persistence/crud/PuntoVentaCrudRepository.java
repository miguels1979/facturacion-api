package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PuntoVentaCrudRepository extends CrudRepository<PuntoVentaEntity, Integer> {

    Optional<PuntoVentaEntity> findByCodigo(Integer codigo);
}
