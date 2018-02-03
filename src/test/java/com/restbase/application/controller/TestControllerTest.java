package com.restbase.application.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.restbase.application.controller.TestController;
import com.restbase.model.service.TestService;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes={TestController.class})
@EnableWebMvc
public class TestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TestService service;
	
	@Test
	public void methodGetShouldReturnNotFoundIfNothingIsFound() throws Exception {
		List<com.restbase.model.domain.Test> tests = new ArrayList<>();
		Mockito.when(service.list()).thenReturn(tests);		
		mockMvc.perform(
					get("/tests/")					
					.accept(MediaType.APPLICATION_JSON)
				
				)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));		
		
	}
	
	@Test
	public void methodGetShouldReturnCorrectValues() throws Exception {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		com.restbase.model.domain.Test test1 = new com.restbase.model.domain.Test(id1);
		com.restbase.model.domain.Test test2 = new com.restbase.model.domain.Test(id2);
		List<com.restbase.model.domain.Test> tests = Arrays.asList(test1, test2);
		
		Mockito.when(service.list()).thenReturn(tests);		
		mockMvc.perform(
					get("/tests")					
					.accept(MediaType.APPLICATION_JSON)					
				
				)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].uuid", equalTo(id1.toString())))
                .andExpect(jsonPath("$.[1].uuid", equalTo(id2.toString())))
                ;
                	
		
	}
	
	@Test
	public void methodGetShouldResultFoundIfRequestedIdExists() throws Exception {
		UUID id = UUID.randomUUID();
		com.restbase.model.domain.Test test1 = new com.restbase.model.domain.Test(id);
		
		
		Mockito.when(service.findByUuid(Mockito.eq(id))).thenReturn(test1);		
		mockMvc.perform(
					get("/tests/"+id)				
					.accept(MediaType.APPLICATION_JSON)		
				)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", equalTo(id.toString())));
    	
	}
	
	@Test
	public void methodGetShouldResultNotFoundIfRequestedIdIsNotAValidUuid() throws Exception {
		String id = "42";			
		mockMvc.perform(
					get("/tests/"+id)				
					.accept(MediaType.APPLICATION_JSON)		
				)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
                
                
    	
	}
	
	@Test
	public void methodGetShouldResultNotFoundIfRequestedIdDontExist() throws Exception {
		UUID id = UUID.randomUUID();
		
		Mockito.when(service.findByUuid(Mockito.eq(id))).thenReturn(null);		
		mockMvc.perform(
					get("/tests/"+id)				
					.accept(MediaType.APPLICATION_JSON)		
				)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
			
                	
		
	}

}
