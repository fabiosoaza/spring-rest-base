package com.restbase.application.rest.controller.interceptor;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.restbase.application.constant.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MDCInterceptor  extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String tracerBullet = request.getHeader(Constants.TRACER_BULLET_HEADER);

		if (!StringUtils.isEmpty(tracerBullet)) {
			setTracerBullet(tracerBullet);
			log.debug("Header {} value put into MDC is {}", Constants.TRACER_BULLET, tracerBullet);
		} else {
			tracerBullet = UUID.randomUUID().toString();
			setTracerBullet(tracerBullet);
			log.debug("Header {} is empty. Generated local {} value is {}", Constants.TRACER_BULLET, Constants.TRACER_BULLET, tracerBullet);
		}

		StopWatch watch = new StopWatch();
		watch.start();

		filterChain.doFilter(request, response);

		watch.stop();
		log.info("Request processed in {} ms", watch.getTotalTimeMillis());

		MDC.remove(Constants.TRACER_BULLET);

	}

	protected void setTracerBullet(String tracerBullet) {
		MDC.put(Constants.TRACER_BULLET, tracerBullet);
	}

}
