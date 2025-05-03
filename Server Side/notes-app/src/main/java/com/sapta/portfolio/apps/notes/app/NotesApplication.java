package com.sapta.portfolio.apps.notes.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@SpringBootApplication
@Configuration
public class NotesApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry
//					.addMapping("**")
//					.allowedOrigins("*")
//					.allowedMethods("*")
//					.exposedHeaders("*")
//					.allowedHeaders("*")				
//					.allowCredentials(false);										
//				
//			}
//		};
//	}
	

//	@Bean
//	public

}
