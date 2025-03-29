package com.kosmostecnologia.facturador.domain.repository;

import com.kosmostecnologia.facturador.persistence.entity.ItemEntity;

import java.util.Optional;

public interface IItemRepository {

    Optional<ItemEntity> findById(Integer id);
}
