package com.restbase.model.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restbase.model.domain.Todo;
import com.restbase.model.repository.TodoRepository;
import com.restbase.model.validator.ConstraintValidators;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@CacheConfig
@Service
@Transactional
public class TodoService {

	private static final String ID_COULD_NOT_BE_FOUND_MSG = "Id %s could not be found";

	private TodoRepository todoRepository;

	@Autowired
	public TodoService(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	public List<Todo> list() {
		log.info("Listing Todos");
		return todoRepository.findAll();

	}

	public void save(Todo todo) {
		log.info("Listing Todos");
		todoRepository.save(todo);
	}
	
	public Optional<Todo> findByUuid(UUID uuid) {
		log.info("Finding Todo By UUID");
		ConstraintValidators.checkIfParameterIsNull(uuid, "uuid");
		return todoRepository.findOneByUuid(uuid);
	}
	
	public void deleteByUUID(UUID uuid) {
		log.info("Deleting Todo");		
		Optional<Todo> toDelete = findByUuid(uuid);
		String message = String.format(ID_COULD_NOT_BE_FOUND_MSG, uuid);
		ConstraintValidators.checkPresent(toDelete, message);
		toDelete.ifPresent(o -> todoRepository.deleteById(o.getId()));
	}


}
