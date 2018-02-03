package com.restbase.application.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.restbase.model.service.CacheTesterService;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes={CacheTesterController.class})
@EnableWebMvc
public class CacheTesterControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
		
	@MockBean
	private CacheTesterService cacheTesterService;
	
		
	@Test
	public void methodGetShouldResultNotFoundIfRequestedIdDontExist() throws Exception {
		Mockito.when(cacheTesterService.getPrimeNumberAtPosition(Long.valueOf(0L))).thenReturn(Long.valueOf(1L));		
		mockMvc.perform(
					get("/cacheTester/cached-prime-number").param("number", "0")				
					.accept(MediaType.TEXT_PLAIN)		
				)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The prime number at position 0 is 1.")))
                ;                
		
	}

}
