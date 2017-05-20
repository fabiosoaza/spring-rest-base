package com.restbase.controller.interceptor;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.restbase.model.constant.Constants;

public class MDCInterceptor  extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(MDCInterceptor.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String tracerBullet = request.getHeader("x-"+Constants.TRACER_BULLET);

		if (!StringUtils.isEmpty(tracerBullet)) {
			MDC.put(Constants.TRACER_BULLET, tracerBullet);
			logger.debug("Header {} value put into MDC", Constants.TRACER_BULLET);
		} else {
			tracerBullet = UUID.randomUUID().toString();
			MDC.put(Constants.TRACER_BULLET, tracerBullet);
			logger.debug("Header {} is empty. Generated local {} value is {}", Constants.TRACER_BULLET, Constants.TRACER_BULLET, tracerBullet);
		}

		filterChain.doFilter(request, response);
		MDC.remove(Constants.TRACER_BULLET);

	}

}
