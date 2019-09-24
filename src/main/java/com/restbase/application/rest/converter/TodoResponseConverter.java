package com.restbase.application.rest.converter;

import org.springframework.stereotype.Component;

import com.restbase.application.rest.dto.TodoResponse;
import com.restbase.model.domain.Todo;

@Component
public class TodoResponseConverter {

	public Todo convert(TodoResponse dto) {
		return new Todo(dto.getUuid(), dto.getTitle(), dto.getDescription(), dto.isCompleted());
	}
	
	public TodoResponse convert(Todo todo) {
		TodoResponse todoResponse = new TodoResponse();
		todoResponse.setUuid(todo.getUuid());
		todoResponse.setTitle(todo.getTitle());
		todoResponse.setDescription(todo.getDescription());
		todoResponse.setCompleted(todo.isCompleted());
		return todoResponse;
	}
	
}
