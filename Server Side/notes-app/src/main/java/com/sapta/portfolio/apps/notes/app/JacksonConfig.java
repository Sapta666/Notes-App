package com.sapta.portfolio.apps.notes.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

//@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();        
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        return objectMapper;
    }
	
//    public GsonProperties gson() {
//    	GsonProperties gsonProperties = new GsonProperties();
//    	gsonProperties.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
//    	
//    	return gsonProperties;
//    }
}