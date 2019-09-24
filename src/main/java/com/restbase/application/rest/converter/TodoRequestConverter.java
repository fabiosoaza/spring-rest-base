package com.restbase.application.rest.converter;

import org.springframework.stereotype.Component;

import com.restbase.application.rest.dto.TodoRequest;
import com.restbase.model.domain.Todo;

@Component
public class TodoRequestConverter {

	public Todo convert(TodoRequest dto) {
		return new Todo(dto.getUuid(), dto.getTitle(), dto.getDescription(), dto.isCompleted());
	}
	
	public TodoRequest convert(Todo todo) {
		TodoRequest todoRequest = new TodoRequest();
		todoRequest.setUuid(todo.getUuid());
		todoRequest.setTitle(todo.getTitle());
		todoRequest.setDescription(todo.getDescription());
		todoRequest.setCompleted(todo.isCompleted());
		return todoRequest;
	}
	
}
