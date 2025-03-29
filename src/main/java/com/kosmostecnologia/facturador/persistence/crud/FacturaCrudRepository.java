package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.FacturaEntity;
import org.springframework.data.repository.CrudRepository;

public interface FacturaCrudRepository extends CrudRepository<FacturaEntity, Long> {
}
