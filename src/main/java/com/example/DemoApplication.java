package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    @Bean
    CommandLineRunner runner() {
        return args -> {
            reservationDao.createTables();
            for (var name : List.of("spring", "spring boot", "spring cloud", "doma")) {
                Reservation r = new Reservation();
                r.name = name;
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
}

