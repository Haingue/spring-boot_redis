package com.tmmf.test.service;

import com.tmmf.test.dto.UserDto;
import com.tmmf.test.entity.ItemEntity;
import com.tmmf.test.entity.UserEntity;
import com.tmmf.test.exception.UserNotFoundException;
import com.tmmf.test.exception.UserNotValidException;
import com.tmmf.test.mapper.UserMapper;
import com.tmmf.test.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemService itemService;


    private Optional<UserEntity> findOne (String login) {
        return userRepository.findById(login);
    }

    private void saveUserItems (UserEntity newEntity) {
        newEntity.getItems().stream().map(ItemEntity::getId).forEach(itemService::deleteItem);
    }

    private void updateUserItems (UserEntity existingEntity, UserEntity newEntity) {
        if (existingEntity != null) {
            existingEntity.getItems().stream().map(ItemEntity::getId).forEach(itemService::deleteItem);
        }
        newEntity.getItems().stream().map(ItemEntity::getId).forEach(itemService::deleteItem);
    }

    private UserEntity saveUser (UserEntity entity) {
        Optional<UserEntity> existingUser = userRepository.findById(entity.getLogin());
        if (existingUser.isPresent()) {
            updateUserItems(existingUser.get(), entity);
        } else {
            saveUserItems(entity);
        }
        entity = userRepository.save(entity);
        return entity;
    }

    private boolean isValid (UserDto dto) {
        return dto.login() != null && !dto.login().isBlank();
    }

    public List<UserDto> loadAllUser () {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserMapper::entityToDto)
                .toList();
    }

    public UserDto createUser (UserDto dto) {
        if (!isValid(dto)) throw new UserNotValidException();
        UserEntity entity = UserMapper.dtoToEntity(dto);
        entity = saveUser(entity);
        return UserMapper.entityToDto(entity);
    }

    public UserDto updateUser (UserDto dto) {
        if (!isValid(dto)) throw new UserNotValidException();
        if (findOne(dto.login()).isEmpty()) throw new UserNotFoundException();
        UserEntity entity = UserMapper.dtoToEntity(dto);
        entity = saveUser(entity);
        return UserMapper.entityToDto(entity);
    }

    public void deleteUser (String login) {
        if (login == null || !login.isBlank()) throw new UserNotValidException();
        if (findOne(login).isEmpty()) throw new UserNotFoundException();
        userRepository.deleteById(login);
    }
}
