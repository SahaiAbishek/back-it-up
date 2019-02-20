package com.app.abhi.backup.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@org.springframework.web.bind.annotation.ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<String> handleAccessDeniedException(
      Exception ex, WebRequest request) {
        return new ResponseEntity<>("Applcation Exception",org.springframework.http.HttpStatus.NOT_ACCEPTABLE);
    }

}
