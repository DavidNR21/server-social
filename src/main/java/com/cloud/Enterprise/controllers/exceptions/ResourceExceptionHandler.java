package com.cloud.Enterprise.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cloud.Enterprise.services.exceptions.DatabaseException;
import com.cloud.Enterprise.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;




@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFpound(ResourceNotFoundException e, HttpServletRequest req){
		String error = "Recurso nao encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), req.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> dataBase(DatabaseException e, HttpServletRequest req){
		String error = "Erro no banco de dados";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), req.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

}
