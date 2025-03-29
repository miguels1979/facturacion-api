package com.kosmostecnologia.facturador.domain.repository;

import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface IPuntoVentaRepository {
    Optional<PuntoVentaEntity>findById(Integer id);
    Optional<PuntoVentaEntity>findByCodigo(Integer codigo);
}
