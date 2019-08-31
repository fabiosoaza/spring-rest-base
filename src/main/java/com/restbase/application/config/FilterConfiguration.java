package com.restbase.application.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restbase.application.rest.controller.interceptor.MDCInterceptor;

@Configuration
public class FilterConfiguration {
	
	@Bean
	public FilterRegistrationBean<MDCInterceptor> mdcFilterRegistrationBean() {
		FilterRegistrationBean<MDCInterceptor> registrationBean = new FilterRegistrationBean<>();
		MDCInterceptor mdcFilter = new MDCInterceptor();
		registrationBean.setFilter(mdcFilter);
		registrationBean.setOrder(1);
		return registrationBean;
	}

}
