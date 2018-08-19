package com.restbase.model.dto;

import java.util.UUID;

public class TestDTO {

	private UUID uuid = UUID.randomUUID();
	
	public TestDTO() {
		// Default constructor
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
}
