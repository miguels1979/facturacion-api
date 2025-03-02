package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.ProductoServicioEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductoServicioCrudRepository extends CrudRepository<ProductoServicioEntity, Long> {

    Optional<ProductoServicioEntity> findByCodigoProducto(Long codigoProducto);
}
