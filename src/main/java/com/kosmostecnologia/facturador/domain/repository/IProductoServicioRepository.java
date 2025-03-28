package com.kosmostecnologia.facturador.domain.repository;

import bo.gob.impuestos.siat.ProductosDto;
import com.kosmostecnologia.facturador.persistence.entity.ProductoServicioEntity;

import java.util.Optional;

public interface IProductoServicioRepository {
    void save(ProductosDto productosDto);
    void deleteAll();
    Optional<ProductoServicioEntity> findByCodigoProducto(Long codigoProducto);

}
