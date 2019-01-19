package com.restbase.application.controller.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import com.restbase.application.constant.Constants;

@RunWith(SpringRunner.class)
public class MDCInterceptorTest {

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private FilterChain filterChain;
	@InjectMocks
	@Spy
	private MDCInterceptor mdcInterceptor;
	
	@Test
	public void shouldCreateARandomTracerBulletIdIfHeaderIsNotSet() throws ServletException, IOException {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		mdcInterceptor.doFilterInternal(request, response, filterChain);
		verify(mdcInterceptor).setTracerBullet(captor.capture());
		String tracerBulletId = captor.getValue();
		assertThat(tracerBulletId).isNotBlank();
	}
	
	@Test
	public void shouldCreateARandomTracerBulletIdIfHeaderIsBlank() throws ServletException, IOException {
		String tracerId = "";
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		setUpRequestHeader(Constants.TRACER_BULLET_HEADER, tracerId);
		mdcInterceptor.doFilterInternal(request, response, filterChain);
		verify(mdcInterceptor).setTracerBullet(captor.capture());
		String tracerBulletId = captor.getValue();
		assertThat(tracerBulletId).isNotBlank();
		assertThat(tracerBulletId).isNotEqualTo(tracerId);
	}
	
	@Test
	public void shouldUseTracerBulletIdFromHeaderIfSet() throws ServletException, IOException {
		String tracerId = UUID.randomUUID().toString();
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		setUpRequestHeader(Constants.TRACER_BULLET_HEADER, tracerId);
		mdcInterceptor.doFilterInternal(request, response, filterChain);
		verify(mdcInterceptor).setTracerBullet(captor.capture());
		String tracerBulletId = captor.getValue();
 		assertThat(tracerBulletId).isNotBlank();
		assertThat(tracerBulletId).isEqualTo(tracerId);
	}
	
	private void setUpRequestHeader(String headerName, String headerValue){
		when(request.getHeader(eq(headerName))).thenReturn(headerValue);
	}

}
