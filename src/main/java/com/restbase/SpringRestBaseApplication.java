package com.restbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages ="com.restbase.model.repository")
@EntityScan(basePackages = "com.restbase.model.domain")
public class SpringRestBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestBaseApplication.class, args);
	}
}
