package com.kosmostecnologia.facturador.persistence;


import bo.gob.impuestos.siat.ParametricaLeyendasDto;
import com.kosmostecnologia.facturador.domain.repository.ILeyendaRepository;
import com.kosmostecnologia.facturador.persistence.crud.LeyendaFacturaCrudRepository;
import com.kosmostecnologia.facturador.persistence.mapper.LeyendaMapper;
import com.kosmostecnologia.facturador.persistence.view.LeyendaView;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LeyendaRepository implements ILeyendaRepository {

    private final LeyendaMapper leyendaMapper;
    private final LeyendaFacturaCrudRepository leyendaFacturaCrudRepository;

    public LeyendaRepository(LeyendaMapper leyendaMapper, LeyendaFacturaCrudRepository leyendaFacturaCrudRepository) {
        this.leyendaMapper = leyendaMapper;
        this.leyendaFacturaCrudRepository = leyendaFacturaCrudRepository;
    }

    @Override
    public void save(ParametricaLeyendasDto parametricaLeyendasDto) {
        this.leyendaFacturaCrudRepository
                .save(this.leyendaMapper.toLeyendaFacturaEntity(parametricaLeyendasDto));
    }

    @Override
    public void deleteAll() {
        this.leyendaFacturaCrudRepository.deleteAll();
    }

    @Override
    public Optional<LeyendaView> obtenerLeyenda(String codigoActividad) {
       return this.leyendaFacturaCrudRepository.obtenerLeyenda(codigoActividad);
    }
}
