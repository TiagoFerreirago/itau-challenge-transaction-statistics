package com.Itauchallenge.transaction.statistics.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomizedExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomizedExceptionHandler.class);
	
	 @ExceptionHandler({
	        CustomizedBadRequestException.class,
	        HttpMessageNotReadableException.class,
	        MethodArgumentNotValidException.class,
	        BindException.class
	    })
	public ResponseEntity<Void> badRequestExceptionHandler(Exception ex){
		logger.warn("Requisição inválida: {}", ex.getMessage());
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
