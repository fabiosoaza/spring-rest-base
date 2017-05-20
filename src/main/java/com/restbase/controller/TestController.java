package com.restbase.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restbase.model.domain.Test;
import com.restbase.model.service.TestService;

@RestController
@RequestMapping("/tests")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService service;	
		
	@RequestMapping(method = RequestMethod.GET)
	public List<Test> list(){
		logger.info("Request Listing");
		return service.list();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UUID> save(@RequestBody Test test){
		logger.info("Request Saving");
		service.save(test);
		return new ResponseEntity<>(test.getUuid(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Test view(@PathVariable("id") String id){
		logger.info("Request Viewing, id{}.", id);
		return service.findByUuid(UUID.fromString(id));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public Test update(@PathVariable("id") String id, @RequestBody Test test){
		logger.info("Request updating, id{}.", id);
		service.update(UUID.fromString(id), test);
		return test;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<UUID> delete(@PathVariable("id") String id){
		logger.info("Request updating, id{}.", id);
		UUID uuid = UUID.fromString(id);
		Test test = service.findByUuid(uuid);
		if(test==null){
			logger.info("id {} not found.", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		service.delete(test.getId());
		return new ResponseEntity<>(uuid, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/cached-prime-number")
	public String testSlow(@RequestParam("number") String numberParam){	
		StopWatch watch = new StopWatch();
		watch.start();		
		Long number = new BigDecimal(numberParam).longValue();
		Long primeNumberAtPosition = service.getPrimeNumberAtPosition(number);
		watch.stop();
		return String.format("The prime number at position %s is %s. Total time %s ms.", number, primeNumberAtPosition, watch.getTotalTimeMillis());
	}

}
