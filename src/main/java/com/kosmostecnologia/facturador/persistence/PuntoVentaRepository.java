package com.kosmostecnologia.facturador.persistence;

import com.kosmostecnologia.facturador.domain.repository.IPuntoVentaRepository;
import com.kosmostecnologia.facturador.persistence.crud.PuntoVentaCrudRepository;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PuntoVentaRepository implements IPuntoVentaRepository {

    private final PuntoVentaCrudRepository puntoVentaCrudRepository;

    public PuntoVentaRepository(PuntoVentaCrudRepository puntoVentaCrudRepository) {
        this.puntoVentaCrudRepository = puntoVentaCrudRepository;
    }

    @Override
    public Optional<PuntoVentaEntity> findById(Integer id) {
        return this.puntoVentaCrudRepository.findById(id);
    }

    @Override
    public Optional<PuntoVentaEntity> findByCodigo(Integer codigo) {
        return this.puntoVentaCrudRepository.findByCodigo(codigo);
    }
}
