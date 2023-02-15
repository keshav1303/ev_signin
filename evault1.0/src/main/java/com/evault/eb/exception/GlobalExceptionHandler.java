package com.evault.eb.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SomethingWentWrongException.class)
    public ResponseEntity<String> handlerSomethingWentWrongException(SomethingWentWrongException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchPropertyFoundException.class)
    public ResponseEntity<String> handlerNoSuchPropertyFoundException(NoSuchPropertyFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handlerIOException(IOException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoPropertyFileFoundException.class)
    public ResponseEntity<String> handlerNoPropertyFileFoundException(NoPropertyFileFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("Please change your method request", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
