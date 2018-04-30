package com.lateral.osintbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OsintBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsintBackendApplication.class, args);
	}
}
