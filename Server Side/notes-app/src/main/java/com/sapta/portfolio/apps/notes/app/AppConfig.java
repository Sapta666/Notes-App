package com.sapta.portfolio.apps.notes.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;
import com.sapta.portfolio.apps.notes.app.utils.HibernateUtils;

@Configuration
//@EnableWebSecurity
public class AppConfig implements WebMvcConfigurer {

	@Bean
	public HibernateUtils getHibernateUtilsBean() {
		return new HibernateUtils();
	}

	@Bean
	HelperUtils getHelperUtilsBean() {
		return new HelperUtils();
	}
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("*")
		.allowedHeaders("*");				
//		.allowCredentials(false);	

	}

}
