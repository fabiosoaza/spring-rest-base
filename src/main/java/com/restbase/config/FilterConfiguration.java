package com.restbase.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restbase.controller.interceptor.MDCInterceptor;

@Configuration
public class FilterConfiguration {
	
	@Bean
	public FilterRegistrationBean mdcFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		MDCInterceptor mdcFilter = new MDCInterceptor();
		registrationBean.setFilter(mdcFilter);
		registrationBean.setOrder(1);
		return registrationBean;
}

}
