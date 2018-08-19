package com.restbase.model.dto;

import java.util.UUID;

public class TestDto {

	private UUID uuid = UUID.randomUUID();
	
	public TestDto() {
		// Default constructor
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
}
