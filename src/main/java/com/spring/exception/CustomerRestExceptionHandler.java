package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.payload.MessageResponse;

@ControllerAdvice
public class CustomerRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleException(CustomerNotFoundException e){
		
		MessageResponse error = new MessageResponse(e.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleException(Exception e){
		
		MessageResponse error = new MessageResponse(e.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
