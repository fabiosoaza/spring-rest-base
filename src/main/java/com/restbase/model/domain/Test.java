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

@Entity
@Table(name = "tests")
public class Test {

	@JsonIgnore
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID uuid = UUID.randomUUID();

	public Test() {
	}

	public Test(UUID uuid) {
		super();
		this.uuid = uuid;
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

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Test) && Objects.equals(uuid, ((Test) obj).getUuid());
	}

	@Override
	public String toString() {
		return "Test [uuid=" + uuid + "]";
	}
	
	
	
	
	

}
