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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.restbase.model.domain.Test;
import com.restbase.model.dto.TestDTO;
import com.restbase.model.service.TestService;

@RestController
@RequestMapping("/tests")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;	
	
	public TestController() {
		// default constructor
	}
		
	@GetMapping	
	public @ResponseBody ResponseEntity<List<Test>> list(){
		logger.info("Request Listing");
		List<Test> list = testService.list();
		HttpStatus status = !CollectionUtils.isEmpty(list) ? HttpStatus.OK: HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Test> view(@PathVariable("id") String id){
		logger.info("Request Viewing, id {}.", id);
		Test test = null;
		try{
			test = testService.findByUuid(getUuid(id));
			HttpStatus status = test !=null ? HttpStatus.OK: HttpStatus.NOT_FOUND;
			return ResponseEntity.status(status).body(test);
		}
		catch(IllegalArgumentException ias){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(test);
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
	
	@PostMapping
	public ResponseEntity<String> save(@RequestBody TestDTO testDto){
		logger.info("Request Saving");
		Test test = new Test(testDto);
		testService.save(test);
		Map<String, Object> map = new HashMap<>();
		map.put("uuid", test.getUuid().toString());		
	    String json = new Gson().toJson(map);
		return new ResponseEntity<>(json, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody TestDTO testDto){
		logger.info("Request updating, id {}.", id);
		try{
			Test test = new Test(testDto);
			testService.updateByUUID(getUuid(id), test);
		}
		catch(IllegalArgumentException ex){
			logger.error("Error updating, id "+id+", request "+testDto+".", ex);
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id){
		logger.info("Request removing, id {}.", id);
		try{
			testService.deleteByUUID(getUuid(id));			
		}
		catch(IllegalArgumentException ex){
			logger.error("Error deleting id "+id+".", ex);
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	


}
