package com.restbase.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CacheTesterServiceTest {

	
	private CacheTesterService cacheTesterService = new CacheTesterService();
	
	@Test
	public void testGetPrimeNumberAtPosition() {		
		assertThat(cacheTesterService.getPrimeNumberAtPosition(0L)).isEqualTo(1L);
	}

}
