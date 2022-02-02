package com.story.storyteller.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.user_app.exceptions.UserNotFoundException;
import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;

//@Component
@Service
public class CharacterService {

	private CharacterRepository characterRepository;
	
	@Autowired
	public CharacterService(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public List<Character> getAll() {
		return characterRepository.findAll();
	}
	
	public Character getById(Long id) {
		if (characterRepository.existsById(id)) {
			return characterRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Character with id " + id + " does not exist.");
	}
	
	public List<Character> getByName(String name) {
		if (characterRepository.existsByName(name)) {
			return characterRepository.findByName(name);
		}
		throw new EntityNotFoundException("Character with id " + name + " does not exist.");
	}
	
	public Character create(Character character) {
		Character savedCharacter = characterRepository.save(character);
		return savedCharacter;
	}
	
	public Character update(long id, Character character) {
		if (characterRepository.existsById(id)) {
			Character characterInDb = characterRepository.getById(id);
			characterInDb.setAge(character.getAge());
			characterInDb.setName(character.getName());
			characterInDb.setType(character.getType());
			characterInDb.setConflict(character.getConflict());
			return characterRepository.save(characterInDb);
		} else {
			throw new EntityNotFoundException("Character with id " + id + " does not exist.");
		}
	}
	
	public void delete(Long id) {
		if (characterRepository.existsById(id)) {
			characterRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Character with id " + id + " does not exist");
		}
	}
	
}
