package com.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.model.output.MessageResponse;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleException(CustomerNotFoundException customerNotFoundException) {

        MessageResponse messageResponse = new MessageResponse(customerNotFoundException.getMessage());

        log.error(messageResponse.getMessage(), customerNotFoundException);

        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleException(Exception exception) {

        MessageResponse messageResponse = new MessageResponse(exception.getMessage());

        log.error(messageResponse.getMessage(), exception);

        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleException(BadCredentialsException exception) {

        MessageResponse messageResponse = new MessageResponse(exception.getMessage());

        log.error(messageResponse.getMessage(), exception);

        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }
}
