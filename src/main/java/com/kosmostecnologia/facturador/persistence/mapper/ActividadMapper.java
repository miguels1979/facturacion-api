package com.kosmostecnologia.facturador.persistence.mapper;

import bo.gob.impuestos.siat.ActividadesDto;
import com.kosmostecnologia.facturador.persistence.entity.ActividadEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ActividadMapper.class})
public interface ActividadMapper {

    ActividadEntity toActividadEntity(ActividadesDto actividadesDto);
}
