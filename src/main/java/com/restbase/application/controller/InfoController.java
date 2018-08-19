package com.restbase.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infos")
public class InfoController {
	
	@Value("${version.number}")
	private String versionNumber;
	
	public InfoController() {
		// default constructor
	}
	
	@GetMapping(path="version")	
	public @ResponseBody ResponseEntity<Map<String,String>> version(){
		Map<String,String> map = new HashMap<>();
		map.put("version", versionNumber);
		return ResponseEntity.status(HttpStatus.FOUND).body(map);
	}

}
