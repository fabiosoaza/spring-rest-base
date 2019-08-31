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
		
		Todo converted = todoRequestConverter.convert(new TodoRequest(uuid, title, description, completed));
		
		Todo expected = new Todo(uuid, title, description, completed);
		
		Assertions.assertThat(converted).isEqualTo(expected);
	}
	
	@Test
	public void shouldConvertDomain() {
		
		TodoRequest converted = todoRequestConverter.convert(new Todo(uuid, title, description, completed));
		
		TodoRequest expected = new TodoRequest(uuid, title, description, completed);
		
		Assertions.assertThat(converted).isEqualTo(expected);
	}
	
}
