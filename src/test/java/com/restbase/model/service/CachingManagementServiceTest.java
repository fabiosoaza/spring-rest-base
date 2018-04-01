package com.restbase.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CachingManagementServiceTest {

	@InjectMocks
	private CachingManagementService cachingManagementService = new CachingManagementService();

	@Mock
	private CacheManager cacheManager;

	@Test
	public void shouldListAllCaches() {
		Mockito.when(cachingManagementService.listAllCacheNames()).thenReturn(Arrays.asList("Cache 1", "Cache 2"));
		assertThat(cachingManagementService.listAllCacheNames()).contains("Cache 1", "Cache 2");
	}

}
