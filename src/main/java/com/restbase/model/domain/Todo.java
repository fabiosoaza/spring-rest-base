package com.restbase.model.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	public Boolean isCompleted() {
		return getCompleted();
	}
	
	

}
