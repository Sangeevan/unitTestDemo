package com.example.unitTestDemo;

import com.example.unitTestDemo.model.User;
import com.example.unitTestDemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UnitTestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitTestDemoApplication.class, args);
	}

	// This method preloads users into H2 after tables are created
	@Bean
	CommandLineRunner run(UserRepository repo) {
		return args -> {
			repo.save(new User(1L, "Sangeevan", 30));
			repo.save(new User(2L, "Sangee", 20));
			repo.save(new User(3L, "Geevan", 10));
		};
	}

}
