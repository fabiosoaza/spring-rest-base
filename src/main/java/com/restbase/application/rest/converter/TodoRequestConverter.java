package com.restbase.application.rest.converter;

import org.springframework.stereotype.Component;

import com.restbase.application.rest.dto.TodoRequest;
import com.restbase.model.domain.Todo;

@Component
public class TodoRequestConverter {

	public Todo convert(TodoRequest dto) {		
		return Todo.builder()
				.uuid(dto.getUuid())
				.title(dto.getTitle())
				.description(dto.getDescription())
				.completed(dto.isCompleted())
				.build();
	}
	
	public TodoRequest convert(Todo todo) {
		return TodoRequest.builder()
		.uuid(todo.getUuid())
		.title(todo.getTitle())
		.description(todo.getDescription())
		.completed(todo.isCompleted())
		.build();
	}
	
}
