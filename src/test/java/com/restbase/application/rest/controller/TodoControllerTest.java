package com.restbase.application.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.restbase.application.rest.converter.TodoRequestConverter;
import com.restbase.application.rest.converter.TodoResponseConverter;
import com.restbase.model.domain.Todo;
import com.restbase.model.service.TodoService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure=false)
@SpringBootTest(classes = { TodoController.class })
@EnableWebMvc
public class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoService;

	@SpyBean
	private TodoRequestConverter todoRequestConverter;

	@SpyBean
	private TodoResponseConverter todoResponseConverter;

	@Test
	public void methodGetShouldReturnNotFoundIfNothingIsFound() throws Exception {
		List<Todo> todos = new ArrayList<>();
		when(todoService.list()).thenReturn(todos);
		mockMvc.perform(get("/todos/").accept(MediaType.APPLICATION_JSON)

		).andExpect(status().isNotFound()).andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	public void methodGetShouldReturnCorrectValues() throws Exception {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		Todo todo1 = new Todo(id1, null, null, null);
		Todo todo2 = new Todo(id2, null, null, null);
		List<Todo> todos = Arrays.asList(todo1, todo2);

		when(todoService.list()).thenReturn(todos);
		mockMvc.perform(get("/todos").accept(MediaType.APPLICATION_JSON)

		).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$.[0].uuid", equalTo(id1.toString())))
				.andExpect(jsonPath("$.[1].uuid", equalTo(id2.toString())));

	}

	@Test
	public void methodGetShouldResultFoundIfRequestedIdExists() throws Exception {
		UUID id = UUID.randomUUID();
		Todo todo1 = new Todo(id, null, null, null);

		when(todoService.findByUuid(eq(id))).thenReturn(Optional.of(todo1));
		mockMvc.perform(get("/todos/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.uuid", equalTo(id.toString())));

	}

	@Test
	public void methodGetShouldResultNotFoundIfRequestedIdIsNotAValidUuid() throws Exception {
		String id = "42";
		mockMvc.perform(get("/todos/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(content().string(""));

	}

	@Test
	public void methodGetShouldResultNotFoundIfRequestedIdDontExist() throws Exception {
		UUID id = UUID.randomUUID();

		when(todoService.findByUuid(eq(id))).thenReturn(Optional.empty());
		mockMvc.perform(get("/todos/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(content().string(""));

	}

	@Test
	public void methodPostShouldPersist() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("title", "Create integration tests");
		map.put("description", "Create integration tests");
		map.put("completed", false);
		String json = jsonString(map);
		mockMvc.perform(post("/todos/").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(content().string(containsString("uuid")));

	}

	@Test
	public void methodDeleteShouldThrowAnExceptionIfIdIsEmpty() throws Exception {

		String json = jsonString(new HashMap<>());
		mockMvc.perform(delete("/todos/").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void methodDeleteShouldThrowAnExceptionIfIdIsInvalid() throws Exception {

		String json = jsonString(new HashMap<>());
		mockMvc.perform(delete("/todos/1").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound());
	}

	@Test
	public void methodDeleteShouldThrowAnExceptionIfIdIsNotFound() throws Exception {
		doThrow(new IllegalArgumentException("Id not found")).when(todoService).deleteByUUID(any(UUID.class));

		String json = jsonString(new HashMap<>());
		mockMvc.perform(
				delete("/todos/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound());
	}

	@Test
	public void methodDeleteShouldPersist() throws Exception {
		String json = jsonString(new HashMap<>());
		mockMvc.perform(
				delete("/todos/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());

	}

	private String jsonString(Map<String, Object> map) {
		String json = new Gson().toJson(map);
		return json;
	}

}
