package com.restbase.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.restbase.model.domain.Todo;
import com.restbase.model.repository.TodoRepository;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

	private static final long DEFAULT_ID = 1L;

	@Mock
	private TodoRepository todoRepository;

	@InjectMocks
	private TodoService todoService;

	@Test
	public void shouldResultAListFromRepository() {
		List<Todo> expectedResult = Arrays.asList(new Todo());
		Mockito.when(todoRepository.findAll()).thenReturn(expectedResult);
		todoService.list();
		Mockito.verify(todoRepository).findAll();
	}

	@Test
	public void shouldSaveInRepository() {
		Todo todo = new Todo();
		todoService.save(todo);
		Mockito.verify(todoRepository).save(Mockito.eq(todo));

	}


	@Test(expected = IllegalArgumentException.class)
	public void findByUUIDShouldThrowAnExceptionIfUUIDIsNull() {
		try {
			todoService.findByUuid(null);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).findOneByUuid(Mockito.any(UUID.class));
		}
	}

	@Test
	public void findByUUIDShouldResultNullIfUUIDIsNotFound() {
		UUID uuid = UUID.randomUUID();
		Mockito.when(todoRepository.findOneByUuid(uuid)).thenReturn(Optional.empty());
		Optional<Todo> result = todoService.findByUuid(uuid);
		Mockito.verify(todoRepository).findOneByUuid(Mockito.eq(uuid));
		assertThat(result).isEmpty();
	}

	@Test
	public void findByUUIDShouldResultARecordFromRepository() {
		UUID uuid = UUID.randomUUID();
		Todo todo = Todo.builder()
				.uuid(uuid)				
				.build();
		Optional<Todo> expectedResult = Optional.of(todo);
		Mockito.when(todoRepository.findOneByUuid(uuid)).thenReturn(expectedResult);
		Optional<Todo> result = todoService.findByUuid(uuid);
		Mockito.verify(todoRepository).findOneByUuid(Mockito.eq(uuid));
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteByUUIDShouldThrowAnExceptionIfIdIsNull() {
		try {
			todoService.deleteByUUID(null);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).deleteById(Mockito.any(Long.class));
		}
	}

	@Test
	public void deleteByUUIDShouldThrowAnExceptionIfIdIsNotFound() {
		UUID uuid = UUID.randomUUID();
		Mockito.when(todoRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(Optional.empty());
		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(()->todoService.deleteByUUID(uuid));
		Mockito.verify(todoRepository, Mockito.never()).deleteById(Mockito.any(Long.class));
	}

	@Test
	public void deleteUUIDPkShouldDeleteIfIdIsFound() {
		UUID uuid = UUID.randomUUID();
		Todo todo = Todo.builder()
				.uuid(uuid)				
				.build();
		todo.setId(DEFAULT_ID);
		Mockito.when(todoRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(Optional.of(todo));
		todoService.deleteByUUID(uuid);
		Mockito.verify(todoRepository).deleteById(Mockito.any(Long.class));
	}

}
