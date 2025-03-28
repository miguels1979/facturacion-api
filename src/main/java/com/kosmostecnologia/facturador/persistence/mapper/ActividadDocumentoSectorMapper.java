package com.kosmostecnologia.facturador.persistence.mapper;

import bo.gob.impuestos.siat.ActividadesDocumentoSectorDto;
import com.kosmostecnologia.facturador.persistence.entity.ActividadDocumentoSectorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ActividadDocumentoSectorMapper.class})
public interface ActividadDocumentoSectorMapper {

    ActividadDocumentoSectorEntity toActividadDocumentoSectorEntity(ActividadesDocumentoSectorDto actividadesDocumentoSectorDto);
}
