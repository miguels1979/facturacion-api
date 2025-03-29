package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.ParametroEntity;
import org.springframework.data.repository.CrudRepository;

public interface ParametroCrudRepository extends CrudRepository<ParametroEntity, Long> {
}
