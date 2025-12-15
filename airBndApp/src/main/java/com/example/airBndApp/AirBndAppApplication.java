package com.example.airBndApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirBndAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirBndAppApplication.class, args);
	}

}
