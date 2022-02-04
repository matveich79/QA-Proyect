package com.story.storyteller.exceptions;

import javax.persistence.EntityExistsException;

public class CharacterNameInUse extends EntityExistsException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CharacterNameInUse(String message) {
		super(message);
	}
}
