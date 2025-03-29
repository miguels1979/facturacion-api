package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.SucursalEntity;
import org.springframework.data.repository.CrudRepository;

public interface SucursalCrudRepository extends CrudRepository<SucursalEntity, Integer> {
}
