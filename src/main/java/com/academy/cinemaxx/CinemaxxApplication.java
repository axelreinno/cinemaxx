package com.academy.cinemaxx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemaxxApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaxxApplication.class, args);
	}
}
