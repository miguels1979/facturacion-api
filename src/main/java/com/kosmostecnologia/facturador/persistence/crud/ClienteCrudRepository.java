package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClienteCrudRepository extends CrudRepository<ClienteEntity, Integer> {
}
