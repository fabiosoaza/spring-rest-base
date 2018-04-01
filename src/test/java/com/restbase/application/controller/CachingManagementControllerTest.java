package com.restbase.application.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.restbase.model.service.CachingManagementService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = { CachingManagementController.class })
@EnableWebMvc
public class CachingManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CachingManagementService cachingManagementService;

	@Test
	public void shouldListAllCacheNames() throws Exception {
		Mockito.when(cachingManagementService.listAllCacheNames()).thenReturn(Arrays.asList("Cache 1", "Cache 2"));
		MvcResult result = mockMvc.perform(get("/cachingManagement").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Cache 1"))

				).andReturn();
		System.out.println(result.getResponse().getContentAsString());

	}

}
