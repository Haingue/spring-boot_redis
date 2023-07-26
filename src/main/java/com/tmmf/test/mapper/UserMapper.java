package com.tmmf.test.mapper;

import com.tmmf.test.dto.UserDto;
import com.tmmf.test.entity.UserEntity;

public class UserMapper {
    public static UserDto entityToDto (UserEntity entity) {
        return new UserDto(
                entity.getLogin(),
                entity.getPassword(),
                entity.getItems().stream().map(ItemMapper::entityToDto).toList()
        );
    }
    public static UserEntity dtoToEntity (UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setLogin(dto.login());
        entity.setPassword(dto.password());
        entity.setItems(dto.items().stream().map(ItemMapper::dtoToEntity).toList());
        return entity;
    }
}
