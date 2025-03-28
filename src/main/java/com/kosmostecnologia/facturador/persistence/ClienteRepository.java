package com.kosmostecnologia.facturador.persistence;

import com.kosmostecnologia.facturador.domain.repository.IClienteRepository;
import com.kosmostecnologia.facturador.persistence.crud.ClienteCrudRepository;
import com.kosmostecnologia.facturador.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteRepository implements IClienteRepository {

    private final ClienteCrudRepository clienteCrudRepository;

    public ClienteRepository(ClienteCrudRepository clienteCrudRepository) {
        this.clienteCrudRepository = clienteCrudRepository;
    }

    @Override
    public Optional<ClienteEntity> findById(Integer id) {
        return this.clienteCrudRepository.findById(id);

    }
}
