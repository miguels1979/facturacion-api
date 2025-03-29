package com.kosmostecnologia.facturador.persistence;

import com.kosmostecnologia.facturador.domain.repository.IItemRepository;
import com.kosmostecnologia.facturador.persistence.crud.ItemCrudRepository;
import com.kosmostecnologia.facturador.persistence.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ItemRepository implements IItemRepository {

    private final ItemCrudRepository itemCrudRepository;

    public ItemRepository(ItemCrudRepository itemCrudRepository) {
        this.itemCrudRepository = itemCrudRepository;
    }

    @Override
    public Optional<ItemEntity> findById(Integer id) {
        return this.itemCrudRepository.findById(id);
    }
}
