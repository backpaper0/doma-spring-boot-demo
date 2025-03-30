package com.example;

import java.util.List;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.seasar.doma.jdbc.criteria.option.LikeOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ReservationDao reservationDao;

	@Autowired
	QueryDsl queryDsl;

	@Bean
	ApplicationRunner runner() {
		return args -> {
			reservationDao.createTables();
			for (var name : List.of("spring", "spring boot", "spring cloud", "doma")) {
				Reservation r = new Reservation(null, name);
				reservationDao.insert(r);
			}
		};
	}

	@RequestMapping(path = "/")
	List<Reservation> all() {
		return reservationDao.selectAll();
	}

	@RequestMapping(path = "/", params = "name")
	List<Reservation> name(@RequestParam String name) {
		return reservationDao.selectByName(name);
	}

	@RequestMapping(path = "/uc")
	List<Reservation> allByUnifiedCriteria() {
		var r = new Reservation_();
		return queryDsl.from(r).orderBy(c -> c.asc(r.name)).fetch();
	}

	@RequestMapping(path = "/uc", params = "name")
	List<Reservation> nameByUnifiedCriteria(@RequestParam String name) {
		var r = new Reservation_();
		return queryDsl.from(r).where(c -> c.like(r.name, name, LikeOption.prefix())).fetch();
	}

}
