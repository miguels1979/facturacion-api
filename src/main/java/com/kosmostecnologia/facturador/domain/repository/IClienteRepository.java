package com.kosmostecnologia.facturador.domain.repository;

import com.kosmostecnologia.facturador.persistence.entity.ClienteEntity;

import java.util.Optional;

public interface IClienteRepository {

    Optional<ClienteEntity> findById(Integer id);
}
