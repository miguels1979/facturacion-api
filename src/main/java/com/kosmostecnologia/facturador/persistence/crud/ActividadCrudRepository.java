package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.ActividadEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActividadCrudRepository extends CrudRepository<ActividadEntity, Long> {
}
