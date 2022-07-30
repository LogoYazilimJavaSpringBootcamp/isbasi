package com.logo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(IsbasiException.class)
	public ResponseEntity<ErrorResponse> handleIsbasiException(IsbasiException exception) {
		ErrorResponse response = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
		
		log.info("IsbasiException Occured :: response: {}", response.toString());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleException(NullPointerException exception) {
		ErrorResponse response = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
		log.info("NullPointerException Occured :: response: {}", response.toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		ErrorResponse response = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
		log.info("Exception Occured :: response: {}", response.toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	


}
