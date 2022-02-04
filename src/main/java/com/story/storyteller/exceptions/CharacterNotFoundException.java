package com.story.storyteller.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Character with id not found")
public class CharacterNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public CharacterNotFoundException(String message) {
		super(message);
	}
}
