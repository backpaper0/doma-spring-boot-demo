package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
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

	final ParameterizedTypeReference<List<Reservation>> reservationsType = new ParameterizedTypeReference<List<Reservation>>() {
	};

	@BeforeEach
	void setUp(@Autowired RestClient.Builder builder) {
		this.http = builder.baseUrl("http://localhost:" + port).build();
	}

	@Test
	void all() {
		var entities = http.get().retrieve().body(reservationsType);
		assertNotNull(entities);
		assertEquals(List.of("doma", "spring", "spring boot", "spring cloud"),
				entities.stream().map(Reservation::name).toList());
	}

	@Test
	void name() {
		final List<Reservation> entities = http.get()
			.uri(builder -> builder.queryParam("name", "spring").build())
			.retrieve()
			.body(reservationsType);
		assertNotNull(entities);
		assertEquals(List.of("spring", "spring boot", "spring cloud"),
				entities.stream().map(Reservation::name).toList());
	}

	@Test
	void allByUnifiedCriteria() {
		var entities = http.get()
			.uri(builder -> builder.path("uc").build())
			.retrieve()
			.body(new ParameterizedTypeReference<List<Reservation>>() {
			});
		assertNotNull(entities);
		assertEquals(List.of("doma", "spring", "spring boot", "spring cloud"),
				entities.stream().map(Reservation::name).toList());
	}

	@Test
	void nameByUnifiedCriteria() {
		var entities = http.get()
			.uri(builder -> builder.path("uc").queryParam("name", "spring").build())
			.retrieve()
			.body(reservationsType);
		assertNotNull(entities);
		assertEquals(List.of("spring", "spring boot", "spring cloud"),
				entities.stream().map(Reservation::name).toList());
	}

	@Test
	void dialectAutoDetection(@Autowired Dialect dialect) {
		assertInstanceOf(H2Dialect.class, dialect);
	}

}
