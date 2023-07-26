package com.tmmf.test.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class HomeControllerTest {

    private static WebTestClient client = WebTestClient.bindToController(new HomeController()).build();

    @BeforeAll
    public static void prepareTesting () {
        Assertions.assertNotNull(client, "The client is null");
    }

    @Test
    void shouldHomePage () {
        client.get().uri("/hello")
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectBody(String.class).isEqualTo("Hello World !")
                );
    }
}