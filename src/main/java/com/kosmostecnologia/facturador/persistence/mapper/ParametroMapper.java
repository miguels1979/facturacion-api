package com.kosmostecnologia.facturador.persistence.mapper;

import bo.gob.impuestos.siat.ParametricasDto;
import com.kosmostecnologia.facturador.persistence.entity.ParametroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ParametroMapper.class})
public interface ParametroMapper {

    ParametroEntity toParametroEntity(ParametricasDto parametricasDto);
}
