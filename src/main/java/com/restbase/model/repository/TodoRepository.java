package com.restbase.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restbase.model.domain.Todo;


@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

		
	List<Todo> findAll(); 
	
	Optional<Todo> findOneByUuid(UUID uuid);
}
