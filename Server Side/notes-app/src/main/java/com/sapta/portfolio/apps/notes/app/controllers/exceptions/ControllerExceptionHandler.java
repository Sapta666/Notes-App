package com.sapta.portfolio.apps.notes.app.controllers.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sapta.portfolio.apps.notes.app.enums.SuccessFailedEnum;
import com.sapta.portfolio.apps.notes.app.models.common.BasicResponseDto;
import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class ControllerExceptionHandler {	
  
    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponseDto>handleGenericException(Exception ex) {
    	ex.printStackTrace();
    	
    	BasicResponseDto errorResponse = new BasicResponseDto(SuccessFailedEnum.FAILED.toString(), HelperUtils.convertStackTraceToString(ex),false);
    	
    	if(ex instanceof ExpiredJwtException)
    		return getExpireJWTExcepionResponse();
    
    	
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
       
    private ResponseEntity<BasicResponseDto> getExpireJWTExcepionResponse() {
    	BasicResponseDto errorResponse = new BasicResponseDto(SuccessFailedEnum.FAILED.toString(), "LOGIN EXPIRED!.",true);
    	
    	return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}