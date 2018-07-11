package com.bridgelabz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * purpose-To start the application.using @SpringBootApplication annotation
 * indicates this is our spring boot app and start from here
 * 
 * @author yuga
 *
 */
@SpringBootApplication
public class SpringBootWithMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithMongoDbApplication.class, args);
	}
}
