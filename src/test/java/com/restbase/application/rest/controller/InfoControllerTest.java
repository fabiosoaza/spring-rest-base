package com.restbase.application.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.restbase.application.rest.controller.InfoController;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes={InfoController.class})
@EnableWebMvc
@TestPropertySource(properties = { "version.number=1.0.0-SNAPSHOT"})
public class InfoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private static final String VERSION_NUMBER = "1.0.0-SNAPSHOT";
	
	@Test
	public void methodVersionShouldReturnApplicationVersion() throws Exception {		
		mockMvc.perform(
					get("/infos/version")					
					.accept(MediaType.APPLICATION_JSON)				
				)
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.version", equalTo(VERSION_NUMBER)));	
		
	}

}
