package com.kosmostecnologia.facturador.persistence;

import bo.gob.impuestos.siat.RespuestaCuis;
import com.kosmostecnologia.facturador.domain.repository.ICuisRepository;
import com.kosmostecnologia.facturador.persistence.crud.CuisCrudRepository;
import com.kosmostecnologia.facturador.persistence.entity.CuisEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import com.kosmostecnologia.facturador.persistence.mapper.CuisMapper;

import java.time.LocalDateTime;

public class CuisRepository implements ICuisRepository {

    private final CuisMapper cuisMapper;
    private final CuisCrudRepository cuisCrudRepository;

    public CuisRepository(CuisMapper cuisMapper, CuisCrudRepository cuisCrudRepository) {
        this.cuisMapper = cuisMapper;
        this.cuisCrudRepository = cuisCrudRepository;
    }

    @Override
    public void save(RespuestaCuis respuestaCuis, PuntoVentaEntity puntoVenta) {
        CuisEntity cuis = this.cuisMapper.toCuisEntity(respuestaCuis);

        cuis.setFechaSolicitada(LocalDateTime.now());
        cuis.setVigente(true);
        cuis.setPuntoVenta(puntoVenta);

        this.cuisCrudRepository.save(cuis);
    }
}
