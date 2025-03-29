package com.kosmostecnologia.facturador.domain.repository;

import bo.gob.impuestos.siat.ActividadesDocumentoSectorDto;

public interface IActividadDocumentoSectorRepository {
    void save(ActividadesDocumentoSectorDto actividadesDocumentoSectorDto);
    void deleteAll();
}
