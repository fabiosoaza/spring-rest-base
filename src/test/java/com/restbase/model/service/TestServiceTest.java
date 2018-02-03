package com.restbase.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.restbase.model.repository.TestRepository;

@RunWith(SpringRunner.class)
public class TestServiceTest {

	private static final long DEFAULT_ID = 1L;

	@Mock
	private TestRepository testRepository;

	@InjectMocks
	private TestService testService;
	
	
	@Test
	public void shouldResultAListFromRepository() {
		List<com.restbase.model.domain.Test> expectedResult = Arrays.asList(new com.restbase.model.domain.Test());
		Mockito.when(testRepository.findAll()).thenReturn(expectedResult);
		testService.list();
		Mockito.verify(testRepository).findAll();
	}
	
	@Test
	public void shouldSaveInRepository(){
		com.restbase.model.domain.Test test = new com.restbase.model.domain.Test();
		testService.save(test);
		Mockito.verify(testRepository).save(Mockito.eq(test));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void updateByUUIDShouldThrowAnExceptionIfIdIsNull(){
		com.restbase.model.domain.Test test = new com.restbase.model.domain.Test();
		try{
			testService.updateByUUID(null, test);
		}
		finally{
			Mockito.verify(testRepository, Mockito.never()).save(Mockito.eq(test));			
		}		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void updateByUUIDShouldThrowAnExceptionIfEntityIsNull(){		
		try{
			UUID uuid = UUID.randomUUID();
			testService.updateByUUID(uuid, null);
		}
		finally{
			Mockito.verify(testRepository, Mockito.never()).save(Mockito.any(com.restbase.model.domain.Test.class));			
		}		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void updateByUUIDShouldThrowAnExceptionIfIdIsNotFound(){		
		try{
			UUID uuid = UUID.randomUUID();
			com.restbase.model.domain.Test test = new com.restbase.model.domain.Test(uuid);
			Mockito.when(testRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(null);
			testService.updateByUUID(uuid, test);
		}
		finally{
			Mockito.verify(testRepository, Mockito.never()).save(Mockito.any(com.restbase.model.domain.Test.class));			
		}		
	}
	
	
	@Test
	public void updateByUUIDShouldUpdateIfIdIsFound(){
		
		UUID uuid = UUID.randomUUID();
		com.restbase.model.domain.Test test = new com.restbase.model.domain.Test(uuid);
		Mockito.when(testRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(test);

		com.restbase.model.domain.Test testToUpdate = new com.restbase.model.domain.Test(uuid);
		testService.updateByUUID(uuid, testToUpdate);

		Mockito.verify(testRepository).save(Mockito.eq(testToUpdate));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void findByPKShouldThrowAnExceptionIfIdIsNull() {
		try {
			testService.findByPk(null);
		} finally {
			Mockito.verify(testRepository, Mockito.never()).findOne(Mockito.any(Long.class));
		}		
	}
	
	@Test
	public void findByPKShouldResultNullIfIdIsNotFound() {		
		Mockito.when(testRepository.findOne(DEFAULT_ID)).thenReturn(null);		
		com.restbase.model.domain.Test result = testService.findByPk(DEFAULT_ID);
		Mockito.verify(testRepository).findOne(Mockito.eq(DEFAULT_ID));
		assertThat(result).isNull();
	}
	
	@Test
	public void findByPKShouldResultARecordFromRepository() {
		com.restbase.model.domain.Test expectedResult = new com.restbase.model.domain.Test();
		expectedResult.setId(DEFAULT_ID);
		Mockito.when(testRepository.findOne(DEFAULT_ID)).thenReturn(expectedResult);		
		com.restbase.model.domain.Test result = testService.findByPk(DEFAULT_ID);
		Mockito.verify(testRepository).findOne(Mockito.eq(DEFAULT_ID));
		assertThat(result).isEqualTo(expectedResult);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void findByUUIDShouldThrowAnExceptionIfUUIDIsNull() {
		try {
			testService.findByUuid(null);
		} finally {
			Mockito.verify(testRepository, Mockito.never()).findOneByUuid(Mockito.any(UUID.class));
		}		
	}
	
	@Test
	public void findByUUIDShouldResultNullIfUUIDIsNotFound() {	
		UUID uuid = UUID.randomUUID();
		Mockito.when(testRepository.findOneByUuid(uuid)).thenReturn(null);		
		com.restbase.model.domain.Test result = testService.findByUuid(uuid);
		Mockito.verify(testRepository).findOneByUuid(Mockito.eq(uuid));
		assertThat(result).isNull();
	}
	
	@Test
	public void findByUUIDShouldResultARecordFromRepository() {
		UUID uuid = UUID.randomUUID();
		com.restbase.model.domain.Test expectedResult = new com.restbase.model.domain.Test(uuid);		
		Mockito.when(testRepository.findOneByUuid(uuid)).thenReturn(expectedResult);	
		com.restbase.model.domain.Test result = testService.findByUuid(uuid);
		Mockito.verify(testRepository).findOneByUuid(Mockito.eq(uuid));
		assertThat(result).isEqualTo(expectedResult);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void deleteByPkShouldThrowAnExceptionIfIdIsNull(){
		try{
			testService.deleteByPk(null);
		}
		finally{
			Mockito.verify(testRepository, Mockito.never()).delete(Mockito.any(Long.class));			
		}		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deleteByPkShouldThrowAnExceptionIfIdIsNotFound(){		
		try{
			com.restbase.model.domain.Test test = new com.restbase.model.domain.Test();
			test.setId(DEFAULT_ID);
			Mockito.when(testRepository.findOne(Mockito.eq(DEFAULT_ID))).thenReturn(null);
			testService.deleteByPk(DEFAULT_ID);
		}
		finally{
			Mockito.verify(testRepository, Mockito.never()).save(Mockito.any(com.restbase.model.domain.Test.class));			
		}			
	}
	
	
	@Test
	public void deleteByPkShouldDeleteIfIdIsFound(){		
		com.restbase.model.domain.Test test = new com.restbase.model.domain.Test();
		test.setId(DEFAULT_ID);
		Mockito.when(testRepository.findOne(Mockito.eq(DEFAULT_ID))).thenReturn(test);
		testService.deleteByPk(DEFAULT_ID);
		Mockito.verify(testRepository).delete(DEFAULT_ID);
	}
	
	

}
