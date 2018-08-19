package com.restbase.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	public void updateByUUIDShouldThrowAnExceptionIfIdIsNull() {
		Todo todo = new Todo();
		try {
			todoService.updateByUUID(null, todo);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).save(Mockito.eq(todo));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateByUUIDShouldThrowAnExceptionIfEntityIsNull() {
		try {
			UUID uuid = UUID.randomUUID();
			todoService.updateByUUID(uuid, null);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).save(Mockito.any(Todo.class));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateByUUIDShouldThrowAnExceptionIfIdIsNotFound() {
		try {
			UUID uuid = UUID.randomUUID();
			Todo todo = new Todo(uuid);
			Mockito.when(todoRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(null);
			todoService.updateByUUID(uuid, todo);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).save(Mockito.any(Todo.class));
		}
	}

	@Test
	public void updateByUUIDShouldUpdateIfIdIsFound() {

		UUID uuid = UUID.randomUUID();
		Todo todo = new Todo(uuid);
		Mockito.when(todoRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(todo);

		Todo todoToUpdate = new Todo(uuid);
		todoService.updateByUUID(uuid, todoToUpdate);

		Mockito.verify(todoRepository).save(Mockito.eq(todoToUpdate));
	}

	@Test(expected = IllegalArgumentException.class)
	public void findByPKShouldThrowAnExceptionIfIdIsNull() {
		try {
			todoService.findByPk(null);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).findById(Mockito.any(Long.class));
		}
	}

	@Test
	public void findByPKShouldResultNullIfIdIsNotFound() {
		Mockito.when(todoRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());
		Todo result = todoService.findByPk(DEFAULT_ID);
		Mockito.verify(todoRepository).findById(Mockito.eq(DEFAULT_ID));
		assertThat(result).isNull();
	}

	@Test
	public void findByPKShouldResultARecordFromRepository() {
		Todo expectedResult = new Todo();
		Mockito.when(todoRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(expectedResult));
		Todo result = todoService.findByPk(DEFAULT_ID);
		Mockito.verify(todoRepository).findById(Mockito.eq(DEFAULT_ID));
		assertThat(result).isEqualTo(expectedResult);
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
		Mockito.when(todoRepository.findOneByUuid(uuid)).thenReturn(null);
		Todo result = todoService.findByUuid(uuid);
		Mockito.verify(todoRepository).findOneByUuid(Mockito.eq(uuid));
		assertThat(result).isNull();
	}

	@Test
	public void findByUUIDShouldResultARecordFromRepository() {
		UUID uuid = UUID.randomUUID();
		Todo expectedResult = new Todo(uuid);
		Mockito.when(todoRepository.findOneByUuid(uuid)).thenReturn(expectedResult);
		Todo result = todoService.findByUuid(uuid);
		Mockito.verify(todoRepository).findOneByUuid(Mockito.eq(uuid));
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteByPkShouldThrowAnExceptionIfIdIsNull() {
		try {
			todoService.deleteByPk(null);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).deleteById(Mockito.any(Long.class));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteByPkShouldThrowAnExceptionIfIdIsNotFound() {
		try {
			Mockito.when(todoRepository.findById(Mockito.eq(DEFAULT_ID))).thenReturn(Optional.empty());
			todoService.deleteByPk(DEFAULT_ID);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).deleteById(Mockito.eq(DEFAULT_ID));
		}
	}

	@Test
	public void deleteByPkShouldDeleteIfIdIsFound() {
		Todo todo = new Todo();
		Mockito.when(todoRepository.findById(Mockito.eq(DEFAULT_ID))).thenReturn(Optional.of(todo));
		todoService.deleteByPk(DEFAULT_ID);
		Mockito.verify(todoRepository).deleteById(Mockito.eq(DEFAULT_ID));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteByUUIDShouldThrowAnExceptionIfIdIsNull() {
		try {
			todoService.deleteByUUID(null);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).deleteById(Mockito.any(Long.class));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteByUUIDShouldThrowAnExceptionIfIdIsNotFound() {
		try {
			UUID uuid = UUID.randomUUID();
			Mockito.when(todoRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(null);
			todoService.deleteByUUID(uuid);
		} finally {
			Mockito.verify(todoRepository, Mockito.never()).deleteById(Mockito.any(Long.class));
		}
	}

	@Test
	public void deleteUUIDPkShouldDeleteIfIdIsFound() {
		UUID uuid = UUID.randomUUID();
		Todo todo = new Todo(uuid);
		todo.setId(DEFAULT_ID);
		Mockito.when(todoRepository.findOneByUuid(Mockito.eq(uuid))).thenReturn(todo);
		todoService.deleteByUUID(uuid);
		Mockito.verify(todoRepository).deleteById(Mockito.any(Long.class));
	}

}
