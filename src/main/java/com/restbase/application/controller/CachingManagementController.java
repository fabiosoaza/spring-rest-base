package com.restbase.application.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restbase.model.service.CachingManagementService;

@RestController
@RequestMapping("/cachingManagement")
public class CachingManagementController {
	
	@Autowired
	private CachingManagementService cachingManagementService;
	
	public CachingManagementController() {
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> listAll(){	
		Collection<String> cacheNames = cachingManagementService.listAllCacheNames();
		return ResponseEntity.status(HttpStatus.OK).body(cacheNames);
	}

}
