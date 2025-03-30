package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	RestClient http;

	@LocalServerPort
	int port;

	@BeforeEach
	void setUp(@Autowired RestClient.Builder builder) {
		this.http = builder.baseUrl("http://localhost:" + port).build();
	}

	@Test
	void all() {
		final List<Reservation> entities = http.get()
			.retrieve()
			.body(new ParameterizedTypeReference<List<Reservation>>() {
			});
		assertEquals(4, entities.size());
	}

	@Test
	void name() {
		final List<Reservation> entities = http.get()
			.uri(builder -> builder.queryParam("name", "spring").build())
			.retrieve()
			.body(new ParameterizedTypeReference<List<Reservation>>() {
			});
		assertEquals(3, entities.size());
	}

	@Test
	void allByUnifiedCriteria() {
		final List<Reservation> entities = http.get()
			.uri(builder -> builder.path("uc").build())
			.retrieve()
			.body(new ParameterizedTypeReference<List<Reservation>>() {
			});
		assertEquals(4, entities.size());
	}

	@Test
	void nameByUnifiedCriteria() {
		final List<Reservation> entities = http.get()
			.uri(builder -> builder.path("uc").queryParam("name", "spring").build())
			.retrieve()
			.body(new ParameterizedTypeReference<List<Reservation>>() {
			});
		assertEquals(3, entities.size());
	}

}
