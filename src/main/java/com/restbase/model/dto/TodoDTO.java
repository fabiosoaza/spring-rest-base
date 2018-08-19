package com.restbase.model.dto;

import java.util.UUID;

public class TodoDTO {

	private UUID uuid = UUID.randomUUID();
	private String title;
	private String description;
	private Boolean completed;
	
	public TodoDTO() {
		// Default DTO
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	
}
