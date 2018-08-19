package com.restbase.model.domain;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restbase.model.dto.TodoDto;

@Entity
@Table(name = "todos")
public class Todo {
	
	@JsonIgnore
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID uuid = UUID.randomUUID();
	
	@Column(name="title", nullable = false)
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="completed", nullable = false)
	private Boolean completed;

	public Todo(UUID uuid, String title, String description, Boolean completed) {
		super();
		this.uuid = uuid;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}	
	
	public Todo(TodoDto dto) {
		this(dto.getUuid(), dto.getTitle(), dto.getDescription(), dto.isCompleted());		
	}
	
	public Todo(UUID uuid) {
		this(uuid, null, null, null);
	}

	public Todo(String title, String description, Boolean completed) {
		this(UUID.randomUUID(), title, description, completed);
	}
	
	public Todo() {
		//Default constructor
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Todo) && Objects.equals(uuid, ((Todo) obj).getUuid());
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", uuid=" + uuid + ", title=" + title + ", description=" + description
				+ ", completed=" + completed + "]";
	}
	
	
	

}
