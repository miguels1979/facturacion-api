package com.kosmostecnologia.facturador.persistence.mapper;

import bo.gob.impuestos.siat.RespuestaCuis;
import com.kosmostecnologia.facturador.persistence.entity.CuisEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {CuisMapper.class})
public interface CuisMapper {
    CuisEntity toCuisEntity(RespuestaCuis respuestaCuis);
}
