package com.story.storyteller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(value = {CharacterNotFoundException.class})
	public ResponseEntity<String> characterNotFoundExceptions(CharacterNotFoundException unfe) {
		return new ResponseEntity<String>(unfe.getMessage(), HttpStatus.NOT_FOUND);
	}

}
