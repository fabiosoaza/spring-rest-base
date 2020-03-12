package com.restbase.application.rest.converter;

import java.util.UUID;

import org.junit.Test;

import com.restbase.application.rest.dto.TodoResponse;
import com.restbase.model.domain.Todo;

import org.assertj.core.api.Assertions;

public class TodoResponseConverterTest {
	
	private TodoResponseConverter todoResponseConverter = new TodoResponseConverter(); 
	
	private static final UUID uuid = UUID.randomUUID(); 
	private static final String title = "Title"; 
	private static final String description = "Description"; 
	private static final Boolean completed = Boolean.TRUE;
	
	@Test
	public void shouldConvertResponse() {
		
		TodoResponse todoResponse = new TodoResponse();
		todoResponse.setUuid(uuid);
		todoResponse.setTitle(title);
		todoResponse.setDescription(description);
		todoResponse.setCompleted(completed);
		
		Todo converted = todoResponseConverter.convert(todoResponse);
		
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
		
		TodoResponse converted = todoResponseConverter.convert(
				Todo.builder()
				.uuid(uuid)
				.title(title)
				.description(description)
				.completed(completed)
				.build()
				);
		
		TodoResponse expected = new TodoResponse();
		expected.setUuid(uuid);
		expected.setTitle(title);
		expected.setDescription(description);
		expected.setCompleted(completed);
		
		Assertions.assertThat(converted).isEqualTo(expected);
	}
	
}
