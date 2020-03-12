package com.restbase.application.rest.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of="uuid")
public class TodoResponse {

	private UUID uuid;
	private String title;
	private String description;
	private Boolean completed;

	public Boolean isCompleted() {
		return getCompleted();
	}	
	
}
