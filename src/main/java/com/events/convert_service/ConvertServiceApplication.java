package com.events.convert_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConvertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvertServiceApplication.class, args);
	}

}
