package com.example.DatabaseConnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DatabaseConnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseConnectionApplication.class, args);
	}
}
