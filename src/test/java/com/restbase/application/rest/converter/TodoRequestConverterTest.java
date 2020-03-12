package com.restbase.application.rest.converter;

import java.util.UUID;

import org.junit.Test;

import com.restbase.application.rest.dto.TodoRequest;
import com.restbase.model.domain.Todo;

import org.assertj.core.api.Assertions;

public class TodoRequestConverterTest {
	
	private TodoRequestConverter todoRequestConverter = new TodoRequestConverter(); 
	
	private static final UUID uuid = UUID.randomUUID(); 
	private static final String title = "Title"; 
	private static final String description = "Description"; 
	private static final Boolean completed = Boolean.TRUE;
	
	@Test
	public void shouldConvertRequest() {
		TodoRequest todoRequest = new TodoRequest();
		todoRequest.setUuid(uuid);
		todoRequest.setTitle(title);
		todoRequest.setDescription(description);
		todoRequest.setCompleted(completed);
		
		Todo converted = todoRequestConverter.convert(todoRequest);
		
		Todo expected = Todo.builder()
				.uuid(uuid)
				.title(title)
				.description(description)
				.completed(completed)
				.build();
		
		Assertions.assertThat(converted).isEqualTo(expected);
	}
	
	@Test
	public void shouldConvertDomain() {
		
		TodoRequest converted = todoRequestConverter.convert(
				Todo.builder()
				.uuid(uuid)
				.title(title)
				.description(description)
				.completed(completed)
				.build()
				);
		
		TodoRequest expected = TodoRequest.builder()
				.uuid(uuid)
				.title(title)
				.description(description)
				.completed(completed)
				.build();
		
		Assertions.assertThat(converted).isEqualTo(expected);
	}
	
}
