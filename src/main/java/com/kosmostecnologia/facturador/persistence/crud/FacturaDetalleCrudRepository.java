package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.FacturaDetalleEntity;
import org.springframework.data.repository.CrudRepository;

public interface FacturaDetalleCrudRepository extends CrudRepository<FacturaDetalleEntity, Long> {


}
