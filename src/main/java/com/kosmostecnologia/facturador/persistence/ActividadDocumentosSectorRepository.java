package com.kosmostecnologia.facturador.persistence;

import bo.gob.impuestos.siat.ActividadesDocumentoSectorDto;
import com.kosmostecnologia.facturador.domain.repository.IActividadDocumentoSectorRepository;
import com.kosmostecnologia.facturador.persistence.crud.ActividadDocumentoSectorCrudRepository;
import com.kosmostecnologia.facturador.persistence.mapper.ActividadDocumentoSectorMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ActividadDocumentosSectorRepository implements IActividadDocumentoSectorRepository {

    private final ActividadDocumentoSectorMapper actividadDocumentoSectorMapper;
    private final ActividadDocumentoSectorCrudRepository actividadDocumentoSectorCrudRepository;

    public ActividadDocumentosSectorRepository(ActividadDocumentoSectorMapper actividadDocumentoSectorMapper, ActividadDocumentoSectorCrudRepository actividadDocumentoSectorCrudRepository) {
        this.actividadDocumentoSectorMapper = actividadDocumentoSectorMapper;
        this.actividadDocumentoSectorCrudRepository = actividadDocumentoSectorCrudRepository;
    }

    @Override
    public void save(ActividadesDocumentoSectorDto actividadesDocumentoSectorDto) {
        this.actividadDocumentoSectorCrudRepository
                .save(this.actividadDocumentoSectorMapper.toActividadDocumentoSectorEntity(actividadesDocumentoSectorDto));
    }

    @Override
    public void deleteAll() {
        this.actividadDocumentoSectorCrudRepository
                .deleteAll();
    }
}
