package com.restbase.application.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.restbase.model.domain.Todo;
import com.restbase.model.repository.TodoRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses =TodoRepository.class)
@EntityScan(basePackageClasses = Todo.class)
public class JpaConfiguration {

}
