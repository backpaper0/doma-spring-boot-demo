package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    TestRestTemplate http;

    @Test
    void all() {
        final ResponseEntity<List<Reservation>> response = http.exchange(
                RequestEntity.get(URI.create("/")).build(),
                new ParameterizedTypeReference<List<Reservation>>() {
                });
        final List<Reservation> entities = response.getBody();
        assertEquals(4, entities.size());
    }

    @Test
    void name() {
        final ResponseEntity<List<Reservation>> response = http.exchange(
                RequestEntity.get(URI.create("/?name=spring")).build(),
                new ParameterizedTypeReference<List<Reservation>>() {
                });
        final List<Reservation> entities = response.getBody();
        assertEquals(3, entities.size());
    }
}
