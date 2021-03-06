package com.restbase.application.rest.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restbase.application.rest.converter.TodoRequestConverter;
import com.restbase.application.rest.converter.TodoResponseConverter;
import com.restbase.application.rest.dto.TodoRequest;
import com.restbase.application.rest.dto.TodoResponse;
import com.restbase.model.domain.Todo;
import com.restbase.model.service.TodoService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {
	

	@Autowired
	private TodoService todoService;
	
	@Autowired
	private TodoRequestConverter todoRequestConverter;
	
	@Autowired
	private TodoResponseConverter todoResponseConverter;
	

	@GetMapping	
	public @ResponseBody ResponseEntity<List<TodoResponse>> list(){
		log.info("Request Listing");
		List<TodoResponse> response = Optional.ofNullable(todoService.list())
				.orElse(Collections.emptyList())
				.stream()
				.map(todo->todoResponseConverter.convert(todo))
				.collect(Collectors.toList());
		HttpStatus status = !response.isEmpty() ? HttpStatus.OK: HttpStatus.NO_CONTENT;
		return ResponseEntity.status(status).body(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TodoResponse> view(@PathVariable("id") String id){
		try{
			Optional<Todo> optional = todoService.findByUuid(getUuid(id));
			TodoResponse response = optional.isPresent() ? todoResponseConverter.convert(optional.get()): null;		
			HttpStatus status = optional.isPresent() ? HttpStatus.OK: HttpStatus.NOT_FOUND;
			return ResponseEntity.status(status).body(response);
		}
		catch(IllegalArgumentException ias){
			log.error("Error viewing item",ias);
			return ResponseEntity.notFound().build();
		}
	}

	private UUID getUuid(String id) {
		UUID uuid = null;
		try{
			uuid = UUID.fromString(id);
		}
		catch(Exception ex){
			log.error("Fail parsing the UUID string", ex);
			throw new IllegalArgumentException("Invalid id");
		}
		return uuid;
	}
	
	@PostMapping
	@PutMapping
	public ResponseEntity<TodoResponse> save(@RequestBody TodoRequest todoDto){
		log.info("Request Saving");
		Todo todo = todoRequestConverter.convert(todoDto);
		todoService.save(todo);
		TodoResponse response = todoResponseConverter.convert(todo);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id){
		try{
			todoService.deleteByUUID(getUuid(id));			
		}
		catch(IllegalArgumentException ex){
			log.error("Error deleting id "+id+".", ex);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	


}
