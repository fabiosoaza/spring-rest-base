package com.restbase.application.rest.dto;

import java.util.Objects;
import java.util.UUID;

public class TodoResponse {

	private UUID uuid;
	private String title;
	private String description;
	private Boolean completed;

	public TodoResponse() {}
		
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

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TodoResponse) && Objects.equals(uuid, ((TodoResponse) obj).getUuid());
	}
	
	
}
