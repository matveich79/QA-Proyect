package com.story.storyteller.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;
import com.story.storyteller.exceptions.CharacterNotFoundException;
import com.story.storyteller.exceptions.CharacterNameInUse;

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
		return characterRepository.findById(id).orElseThrow(() -> {
			return new CharacterNotFoundException("Character with id " + id + " does not exist");
		});
	}
	
	public List<Character> getByName(String name) {
		if (characterRepository.existsByName(name)) {
			return characterRepository.findByName(name);
		}
		throw new CharacterNotFoundException("Character with name " + name + " does not exist");
	}
	
	public Character create(Character character) {
		if (!characterRepository.existsByName(character.getName())) {
			Character savedCharacter = characterRepository.save(character);
			return savedCharacter;
		} else {
			throw new CharacterNameInUse("This name is already been use in other character");
		}
		
	}
	
	public Character update(long id, Character character) {
		if (characterRepository.existsById(id)) {
			Character characterInDb = characterRepository.getById(id);
			
			characterInDb.setName(character.getName());
			characterInDb.setType(character.getType());
			characterInDb.setAge(character.getAge());
			
			characterInDb.setConflict(character.getConflict());
			return characterRepository.save(characterInDb);
		} else {
			throw new CharacterNotFoundException("Character with id " + id + " does not exist");
		}
	}
	
	public String delete(Long id) {
		if (characterRepository.existsById(id)) {
			characterRepository.deleteById(id);
			return "done";
		} else {
			throw new CharacterNotFoundException("Character with id " + id + " does not exist");
		}
	}
	
}
