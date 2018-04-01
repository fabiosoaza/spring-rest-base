package com.restbase.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;


public class TestTest {

	@Test
	public void testHashCodeDifferentObjects() {
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test();
		com.restbase.model.domain.Test obj2 = new com.restbase.model.domain.Test();
		assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());	
	}
	
	@Test
	public void testHashCodeObjectsDiffrentUUID() {
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test();
		com.restbase.model.domain.Test obj2 = new com.restbase.model.domain.Test(UUID.randomUUID());
		assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());		
	}
	
	@Test
	public void testHashCodeObjectsSameUUID() {
		UUID randomUUID = UUID.randomUUID();
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test(randomUUID);
		com.restbase.model.domain.Test obj2 = new com.restbase.model.domain.Test(randomUUID);
		assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode());		
	}
	
	@Test
	public void testEqualsDifferentObjects() {
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test();
		com.restbase.model.domain.Test obj2 = new com.restbase.model.domain.Test();
		assertThat(obj1).isNotEqualTo(obj2);	
		assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());	
	}
	
	@Test
	public void testEqualsObjectsDiffrentUUID() {
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test();
		com.restbase.model.domain.Test obj2 = new com.restbase.model.domain.Test(UUID.randomUUID());
		assertThat(obj1).isNotEqualTo(obj2);	
		assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());	
	}
	
	@Test
	public void testEqualsObjectsSecondObjectIsNull() {
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test();
		com.restbase.model.domain.Test obj2 = null;
		assertThat(obj1).isNotEqualTo(obj2);
	}

	@Test
	public void testEqualsObjectsSameUUID() {
		UUID randomUUID = UUID.randomUUID();
		com.restbase.model.domain.Test obj1 = new com.restbase.model.domain.Test(randomUUID);
		com.restbase.model.domain.Test obj2 = new com.restbase.model.domain.Test(randomUUID);
		assertThat(obj1).isEqualTo(obj2);		
	}
	
	@Test
	public void testEqualsSameObjects() {
		com.restbase.model.domain.Test obj = new com.restbase.model.domain.Test(UUID.randomUUID());
		assertThat(obj).isEqualTo(obj);		
	}
	
}
