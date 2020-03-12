package com.restbase.application.rest.converter;

import org.springframework.stereotype.Component;

import com.restbase.application.rest.dto.TodoResponse;
import com.restbase.model.domain.Todo;

@Component
public class TodoResponseConverter {

	public Todo convert(TodoResponse dto) {
		return Todo.builder()
				.uuid(dto.getUuid())
				.title(dto.getTitle())
				.description(dto.getDescription())
				.completed(dto.isCompleted())
				.build();
	}
	
	public TodoResponse convert(Todo todo) {
		return TodoResponse.builder()
				.uuid(todo.getUuid())
				.title(todo.getTitle())
				.description(todo.getDescription())
				.completed(todo.isCompleted())
				.build();
	}
	
}
