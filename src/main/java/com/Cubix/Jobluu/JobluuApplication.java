package com.Cubix.Jobluu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobluuApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobluuApplication.class, args);
	}

}
