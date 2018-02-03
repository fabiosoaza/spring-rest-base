package com.restbase.application.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restbase.model.service.CacheTesterService;

@RestController
@RequestMapping("/cacheTester")
public class CacheTesterController {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheTesterController.class);
	
	@Autowired
	private CacheTesterService cacheTesterService;
	
	public CacheTesterController() {
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/cached-prime-number")
	public String testSlow(@RequestParam("number") String numberParam){	
		StopWatch watch = new StopWatch();
		watch.start();		
		Long number = new BigDecimal(numberParam).longValue();
		Long primeNumberAtPosition = cacheTesterService.getPrimeNumberAtPosition(number);
		watch.stop();
		String message = String.format("The prime number at position %s is %s. Total time %s ms.", number, primeNumberAtPosition, watch.getTotalTimeMillis());
		logger.info(message);		
		return message;
	}

}
