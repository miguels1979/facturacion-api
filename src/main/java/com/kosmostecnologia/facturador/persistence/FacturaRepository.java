package com.kosmostecnologia.facturador.persistence;

import com.kosmostecnologia.facturador.domain.repository.IFacturaRepository;
import com.kosmostecnologia.facturador.persistence.crud.FacturaCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FacturaRepository implements IFacturaRepository {

    private final FacturaCrudRepository facturaCrudRepository;

    public FacturaRepository(FacturaCrudRepository facturaCrudRepository) {
        this.facturaCrudRepository = facturaCrudRepository;
    }

    @Override
    public Long obtenerNumeroFactura() {
        long cantidad = this.facturaCrudRepository.count();
        return cantidad == 0 ? 1 : cantidad + 1;
    }
}
