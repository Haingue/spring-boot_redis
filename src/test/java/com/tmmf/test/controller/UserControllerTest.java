package com.tmmf.test.controller;

import com.tmmf.test.dto.ItemDto;
import com.tmmf.test.dto.UserDto;
import com.tmmf.test.mapper.UserMapper;
import com.tmmf.test.repository.ItemRepository;
import com.tmmf.test.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Order(1)
    void prepareTesting () {
        Assertions.assertNotNull(webClient, "The client is null");
        Assertions.assertNotNull(userRepository, "The userRepository is null");
    }

    @Test
    @Order(2)
    void shouldLoadUsers () {
        webClient.get().uri("/service/user")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class).value(Objects::nonNull);
    }

//    @Test
//    @Order(3)
//    public void shouldSaveUser () {
//        ItemDto itemDto = new ItemDto(100L, "TEST", "DESC TEST", 50, 10.5, UUID.randomUUID(), LocalDateTime.now());
//        UserDto userDto = new UserDto("Test", "azerty", List.of(itemDto));
//        webClient.post().uri("/service/user")
//                .bodyValue(userDto)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(UserDto.class).value(userResult -> userResult.equals(userDto));
//        Assertions.assertTrue(userRepository.findById(userDto.login()).isPresent());
//        Assertions.assertTrue(itemRepository.findById(itemDto.id()).isPresent());
//    }
//
//    @Test
//    @Order(4)
//    void shouldUpdateUser () {
//        ItemDto itemDto = new ItemDto(100L, "TEST", "DESC TEST", 50, 10.5, UUID.randomUUID(), LocalDateTime.now());
//        UserDto userDto = new UserDto("Test", "azerty", List.of());
//        userRepository.save(UserMapper.dtoToEntity(userDto));
//        userDto.items().add(itemDto);
//        webClient.put().uri("/service/user")
//                .bodyValue(userDto)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(UserDto.class).value(userResult -> userResult.equals(userDto));
//    }
//
//    @Test
//    @Order(5)
//    public void shouldDeleteUser () {
//        ItemDto itemDto = new ItemDto(100L, "TEST", "DESC TEST", 50, 10.5, UUID.randomUUID(), LocalDateTime.now());
//        UserDto userDto = new UserDto("Test", "azerty", List.of(itemDto));
//        userRepository.save(UserMapper.dtoToEntity(userDto));
//        webClient.delete()
//                .uri(uriBuilder -> uriBuilder.path("/service/user").queryParam("id",  userDto.login()).build())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody().isEmpty();
//        Assertions.assertFalse(userRepository.findById(userDto.login()).isPresent());
//    }
//
//    @Test
//    @Order(6)
//    void shouldNotFindUser () {
//        UserDto userDto = new UserDto("Test", "azerty", List.of());        webClient.get().uri("/service/user")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(UserDto.class).doesNotContain(userDto);
//    }


}