package com.restbase.application.rest.dto;

import java.util.Objects;
import java.util.UUID;

public class TodoResponse {

	private UUID uuid;
	private String title;
	private String description;
	private Boolean completed;
	
	public TodoResponse() {}
	
	public TodoResponse(UUID uuid, String title, String description, Boolean completed) {
		super();
		this.uuid = uuid;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Boolean isCompleted() {
		return completed;
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
