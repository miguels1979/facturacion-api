package com.kosmostecnologia.facturador.persistence.crud;

import com.kosmostecnologia.facturador.persistence.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemCrudRepository extends CrudRepository<ItemEntity, Integer> {
}
