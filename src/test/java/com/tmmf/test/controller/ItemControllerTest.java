package com.tmmf.test.controller;

import com.tmmf.test.dto.ItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemControllerTest {

    private static WebTestClient client = WebTestClient.bindToController(new ItemController()).build();

    @BeforeAll
    public static void prepareTesting () {
        Assertions.assertNotNull(client, "The client is null");
    }

    @Test
    @Order(1)
    public void shouldLoadItems () {
        client.get().uri("/service/item")
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectBodyList(ItemDto.class).hasSize(0)
                );
    }

    @Test
    @Order(2)
    public void shouldSaveItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 10, 0.5, LocalDateTime.now());
        client.post().uri("/service/item")
                .bodyValue(itemDto)
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isCreated(),
                        spec -> spec.expectBody(ItemDto.class).value(itemResult -> itemResult.equals(itemDto))
                );
    }

    @Test
    @Order(3)
    public void shouldUpdateItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 5, 0.5, LocalDateTime.now());
        client.post().uri("/service/item")
                .bodyValue(itemDto)
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectBody(ItemDto.class).value(itemResult -> itemResult.equals(itemDto))
                );
    }

    @Test
    @Order(4)
    public void shouldDeleteItem () {
        ItemDto itemDto = new ItemDto(1L, "TEST", "DESC TEST", 5, 0.5, LocalDateTime.now());
        client.delete().uri("/service/item")
                .attribute("id", itemDto.id())
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectBody(ItemDto.class).value(itemResult -> itemResult.equals(itemDto))
                );
    }

}