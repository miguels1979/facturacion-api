package com.kosmostecnologia.facturador.persistence;

import bo.gob.impuestos.siat.ActividadesDto;
import com.kosmostecnologia.facturador.domain.repository.IActividadRepository;
import com.kosmostecnologia.facturador.persistence.crud.ActividadCrudRepository;
import com.kosmostecnologia.facturador.persistence.mapper.ActividadMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ActividadRepository implements IActividadRepository {

    private final ActividadCrudRepository actividadCrudRepository;

    private final ActividadMapper actividadMapper;

    public ActividadRepository(ActividadCrudRepository actividadCrudRepository, ActividadMapper actividadMapper) {
        this.actividadCrudRepository = actividadCrudRepository;
        this.actividadMapper = actividadMapper;
    }


    @Override
    public void save(ActividadesDto actividadesDto) {
        this.actividadCrudRepository.save(this.actividadMapper.toActividadEntity(actividadesDto));

    }

    @Override
    public void deleteAll() {
        this.actividadCrudRepository.deleteAll();
    }
}
