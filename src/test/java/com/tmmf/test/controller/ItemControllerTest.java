package com.tmmf.test.controller;

import com.tmmf.test.dto.ItemDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.class)
class ItemControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @Order(1)
    public void prepareTesting () {
        Assertions.assertNotNull(webClient, "The client is null");
    }

    @Test
    @Order(2)
    public void shouldLoadItems () {
        webClient.get().uri("/service/item")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDto.class).value(Objects::nonNull);
    }

    @Test
    @Order(3)
    public void shouldSaveItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 10, 0.5, UUID.randomUUID(), LocalDateTime.now());
        webClient.post().uri("/service/item")
                .bodyValue(itemDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ItemDto.class).value(itemResult -> itemResult.equals(itemDto));
    }

    @Test
    @Order(4)
    public void shouldUpdateItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 5, 0.5, UUID.randomUUID(), LocalDateTime.now());
        webClient.put().uri("/service/item")
                .bodyValue(itemDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ItemDto.class).value(itemResult -> itemResult.equals(itemDto));
    }

    @Test
    @Order(5)
    public void shouldDeleteItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 5, 0.5, UUID.randomUUID(), LocalDateTime.now());
        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/service/item").queryParam("id",  itemDto.id()).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    @Order(6)
    public void shouldNotFindItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 5, 0.5, UUID.randomUUID(), LocalDateTime.now());
        webClient.get().uri("/service/item")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemDto.class).doesNotContain(itemDto);
    }

}