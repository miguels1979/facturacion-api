package com.kosmostecnologia.facturador.persistence.mapper;

import bo.gob.impuestos.siat.RespuestaCufd;
import com.kosmostecnologia.facturador.persistence.entity.CufdEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CufdMapper.class)
public interface CufdMapper {
    CufdEntity toCufdEntity(RespuestaCufd respuestaCufd);
}
