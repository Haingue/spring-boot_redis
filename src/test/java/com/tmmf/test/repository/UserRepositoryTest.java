package com.tmmf.test.repository;

import com.tmmf.test.entity.ItemEntity;
import com.tmmf.test.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void shouldSaveUserWithoutItem () {
        UserEntity entity = new UserEntity();
        entity.setLogin("Pierre");
        entity.setPassword("Dupont");

        AtomicReference<UserEntity> savedEntity = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            savedEntity.set(userRepository.save(entity));
        }, "Exception during the saving of a user");
        Assertions.assertNotNull(savedEntity.get(), "The user saved is null");
        Assertions.assertTrue(savedEntity.get().getItems().isEmpty());
    }

    @Test
    void shouldSaveUserWithItem () {
        UserEntity entity = new UserEntity();
        entity.setLogin("Pierre");
        entity.setPassword("Dupont");

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setName("Item 1");
        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setId(2L);
        itemEntity2.setName("Item 2");
        entity.getItems().add(itemEntity1);
        entity.getItems().add(itemEntity2);

        AtomicReference<UserEntity> savedEntity = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            savedEntity.set(userRepository.save(entity));
        }, "Exception during the saving of a user");
        Assertions.assertNotNull(savedEntity.get(), "The user saved is null");
        Assertions.assertFalse(savedEntity.get().getItems().isEmpty());

        // with Redis, there is no relation
        // Assertions.assertTrue(itemRepository.findById(1L).isPresent());
        // Assertions.assertTrue(itemRepository.findById(2L).isPresent());
    }
}