package com.kosmostecnologia.facturador.persistence;

import bo.gob.impuestos.siat.RespuestaCufd;
import com.kosmostecnologia.facturador.domain.repository.ICufdRepository;
import com.kosmostecnologia.facturador.persistence.crud.CufdCrudRepository;
import com.kosmostecnologia.facturador.persistence.entity.CufdEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import com.kosmostecnologia.facturador.persistence.mapper.CufdMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class CufdRepository implements ICufdRepository {

    private final CufdMapper cufdMapper;
    private final CufdCrudRepository cufdCrudRepository;

    public CufdRepository(CufdMapper cufdMapper,CufdCrudRepository cufdCrudRepository ) {
        this.cufdMapper = cufdMapper;
        this.cufdCrudRepository = cufdCrudRepository;
    }

    @Override
    public void save(RespuestaCufd respuestaCufd, PuntoVentaEntity puntoVenta) {
        CufdEntity cufd = this.cufdMapper.toCufdEntity(respuestaCufd);
        /*
        Completamos con las columnas que no tienen datos
         */
        cufd.setFechaInicio(LocalDateTime.now());
        cufd.setVigente(true);
        cufd.setPuntoVenta(puntoVenta);

        this.cufdCrudRepository.save(cufd);
    }

    @Override
    public Optional<CufdEntity> findActual(PuntoVentaEntity puntoVenta) {
        return this.cufdCrudRepository.findByPuntoVentaAndVigente(puntoVenta, true);
    }
}
