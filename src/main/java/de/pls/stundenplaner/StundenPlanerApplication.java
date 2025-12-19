package de.pls.stundenplaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class StundenPlanerApplication {

    public static void main(String[] args) {

        System.out.println(UUID.randomUUID());

        SpringApplication.run(StundenPlanerApplication.class, args);

    }

}
