package com.tmmf.test.service;

import com.tmmf.test.dto.ItemDto;
import com.tmmf.test.entity.ItemEntity;
import com.tmmf.test.exception.ItemNotFoundException;
import com.tmmf.test.exception.ItemNotValidException;
import com.tmmf.test.mapper.ItemMapper;
import com.tmmf.test.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private ItemRepository itemRepository;


    private Optional<ItemEntity> findOne (ItemDto entity) {
        return itemRepository.findById(entity.id());
    }

    private ItemEntity saveItem (ItemEntity entity) {
        entity = itemRepository.save(entity);
        return entity;
    }

    private boolean isValid (ItemDto dto) {
        return dto.id() > 0
            && dto.name() != null && !dto.name().isEmpty()
            && dto.deliveryDate() != null;
    }

    public List<ItemDto> loadAllItem () {
        return StreamSupport.stream(itemRepository.findAll().spliterator(), false)
                .map(ItemMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public ItemDto createItem (ItemDto dto) {
        if (!isValid(dto)) throw new ItemNotValidException();
        ItemEntity entity = ItemMapper.dtoToEntity(dto);
        entity = saveItem(entity);
        return ItemMapper.entityToDto(entity);
    }

    public ItemDto updateItem (ItemDto dto) {
        if (!findOne(dto).isPresent()) throw new ItemNotFoundException();
        ItemEntity entity = ItemMapper.dtoToEntity(dto);
        entity = saveItem(entity);
        return ItemMapper.entityToDto(entity);
    }

    public void deleteItem (ItemDto dto) {
        if (!findOne(dto).isPresent()) throw new ItemNotFoundException();
        itemRepository.deleteById(dto.id());
    }
}