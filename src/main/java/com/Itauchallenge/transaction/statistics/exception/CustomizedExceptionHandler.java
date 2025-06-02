package com.Itauchallenge.transaction.statistics.exception;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomizedExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomizedExceptionHandler.class);
	
	@ExceptionHandler(CustomizedBadRequestException.class)
	public ResponseEntity<ExceptionResponse> badRequestExceptionHandler(CustomizedBadRequestException ex, WebRequest http){
		logger.warn("Requisição inválida: {}", ex.getMessage());
		ExceptionResponse response = new ExceptionResponse(http.getDescription(false), OffsetDateTime.now() , ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> genericExceptionHandler(Exception ex, WebRequest http){
		logger.error("Erro interno não tratado: ", ex);
		ExceptionResponse response = new ExceptionResponse(http.getDescription(false), OffsetDateTime.now() , "Ocorreu um erro interno no servidor");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
