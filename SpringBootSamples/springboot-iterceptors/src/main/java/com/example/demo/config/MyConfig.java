package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.iterceptors.OneInterceptor;
import com.example.demo.iterceptors.TwoInterceptor;

@Configuration
public class MyConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		
		registry.addInterceptor(new OneInterceptor()).addPathPatterns("/one/**");
		registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/two/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}	
}
