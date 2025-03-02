package com.kosmostecnologia.facturador.domain.repository;

import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface IPuntoVentaRepository {

    Optional<PuntoVentaEntity>findById(Integer id);
}
