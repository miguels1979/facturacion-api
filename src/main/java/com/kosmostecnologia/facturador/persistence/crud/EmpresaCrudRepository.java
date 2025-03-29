package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.EmpresaEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaCrudRepository extends CrudRepository<EmpresaEntity, Integer> {
}
