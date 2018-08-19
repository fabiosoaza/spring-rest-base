package com.restbase.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.restbase.model.domain.Todo;
import com.restbase.model.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
	
	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
	
	@Autowired
	private TodoService todoService;	
	
	public TodoController() {
		// default constructor
	}
		
	@RequestMapping(method = RequestMethod.GET)	
	public @ResponseBody ResponseEntity<List<Todo>> list(){
		logger.info("Request Listing");
		List<Todo> list = todoService.list();
		HttpStatus status = !CollectionUtils.isEmpty(list) ? HttpStatus.OK: HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Todo> view(@PathVariable("id") String id){
		logger.info("Request Viewing, id {}.", id);
		Todo todo = null;
		try{
			todo = todoService.findByUuid(getUuid(id));
			HttpStatus status = todo !=null ? HttpStatus.OK: HttpStatus.NOT_FOUND;
			return ResponseEntity.status(status).body(todo);
		}
		catch(IllegalArgumentException ias){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(todo);
		}
	}

	private UUID getUuid(String id) {
		UUID uuid = null;
		try{
			uuid = UUID.fromString(id);
		}
		catch(Exception ex){
			logger.error("Fail parsing the UUID string", ex);
			throw new IllegalArgumentException("Invalid id");
		}
		return uuid;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody Todo todo){
		logger.info("Request Saving");
		todoService.save(todo);
		Map<String, Object> map = new HashMap<>();
		map.put("uuid", todo.getUuid().toString());		
	    String json = new Gson().toJson(map);
		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody Todo todo){
		logger.info("Request updating, id {}.", id);
		try{
			todoService.updateByUUID(getUuid(id), todo);
		}
		catch(IllegalArgumentException ex){
			logger.error("Error updating, id "+id+", request "+todo+".", ex);
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id){
		logger.info("Request removing, id {}.", id);
		try{
			todoService.deleteByUUID(getUuid(id));			
		}
		catch(IllegalArgumentException ex){
			logger.error("Error deleting id "+id+".", ex);
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	


}
