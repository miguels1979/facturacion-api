package com.kosmostecnologia.facturador.persistence.mapper;

import bo.gob.impuestos.siat.ProductosDto;
import com.kosmostecnologia.facturador.persistence.entity.ProductoServicioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductoServicioMapper.class})
public interface ProductoServicioMapper {

    ProductoServicioEntity toActividadEntity(ProductosDto productosDto);

}
