package com.tmmf.test.mapper;

import com.tmmf.test.dto.ItemDto;
import com.tmmf.test.entity.ItemEntity;

public class ItemMapper {
    public static ItemDto entityToDto (ItemEntity entity) {
        return new ItemDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getLeadTime(),
                entity.getWeight(),
                entity.getOwner(),
                entity.getDeliveryDate()
        );
    }
    public static ItemEntity dtoToEntity (ItemDto dto) {
        ItemEntity entity = new ItemEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setLeadTime(dto.leadTime());
        entity.setWeight(dto.weight());
        entity.setOwner(dto.owner());
        entity.setDeliveryDate(dto.deliveryDate());
        return entity;
    }
}
