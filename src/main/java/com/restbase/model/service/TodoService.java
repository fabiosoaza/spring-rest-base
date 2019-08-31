package com.restbase.model.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restbase.model.domain.Todo;
import com.restbase.model.repository.TodoRepository;
import com.restbase.model.validator.ConstraintValidators;

@CacheConfig
@Service
@Transactional
public class TodoService {

	private static final String ID_COULD_NOT_BE_FOUND_MSG = "Id %s could not be found";

	private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

	@Autowired
	private TodoRepository todoRepository;

	public TodoService() {
		// default constructor
	}

	public List<Todo> list() {
		logger.info("Listing Todos");
		return todoRepository.findAll();

	}

	public void save(Todo todo) {
		logger.info("Listing Todos");
		todoRepository.save(todo);
	}
	
	public Optional<Todo> findByUuid(UUID uuid) {
		logger.info("Finding Todo By UUID");
		ConstraintValidators.checkIfParameterIsNull(uuid, "uuid");
		return todoRepository.findOneByUuid(uuid);
	}
	
	public void deleteByUUID(UUID uuid) {
		logger.info("Deleting Todo");		
		Optional<Todo> toDelete = findByUuid(uuid);
		String message = String.format(ID_COULD_NOT_BE_FOUND_MSG, uuid);
		ConstraintValidators.checkPresent(toDelete, message);
		todoRepository.deleteById(toDelete.get().getId());
	}


}
