package com.academy.cinemaxx;

import com.academy.cinemaxx.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaxxApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaxxApplication.class, args);
	}
}
