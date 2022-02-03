package com.story.storyteller.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;
import com.story.storyteller.service.CharacterService;


@RestController // this is a bean that should be stored in the app context
@RequestMapping(path = "/character") // access this controller at localhost:8080/user
public class CharacterController {
	
	private CharacterService characterService;
	
	@Autowired
	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}
	
	//private static long counter = 1;

	//private List<User> users = new ArrayList<>(List.of(new User(counter++, "Fred", "Daly", 33), new User(counter++, "Sarah", "Smith", 29)));
	
	// READ ALL
	@GetMapping // localhost:8080/user
	public ResponseEntity<List<Character>> getCharacters() {
		ResponseEntity<List<Character>> characters = ResponseEntity.ok(characterService.getAll());
		return characters;
	}
	
	
	// READ BY ID
	@RequestMapping(path = "/id/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Character> getCharacterById(@PathVariable("id") long id) {
		Character savedCharacter = characterService.getById(id);
		
		ResponseEntity<Character> response = ResponseEntity.status(HttpStatus.OK)
													  .body(savedCharacter);
		return response;
	}
	
	
	// READ BY NAME
	@RequestMapping(path = "/name/{name}", method = { RequestMethod.GET})
	public ResponseEntity<List<Character>> getCharacterByName(@PathVariable("name") String name) {
		ResponseEntity<List<Character>> characters = ResponseEntity.ok(characterService.getByName(name));
		return characters;
	}
	
	
	// CREATE
	@PostMapping
	public ResponseEntity<Character> createCharacter(@Valid @RequestBody Character character) {
		
		Character savedCharacter = characterService.create(character);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/character/" + String.valueOf(savedCharacter.getId()));
		// headers.add("Contest-Type", "application/json");
		
		ResponseEntity<Character> response = new ResponseEntity<Character>(savedCharacter, headers, HttpStatus.CREATED);
		return response;
	}
	
	//UPDATE
	@PutMapping("/id/{id}")
	public ResponseEntity<Character> updateCharacter(@PathVariable("id") long id, @Valid @RequestBody Character character) {

		Character updatedCharacter = characterService.update(id, character);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/character/id/" + String.valueOf(updatedCharacter.getId()));
		
		return new ResponseEntity<Character>(updatedCharacter, headers, HttpStatus.ACCEPTED);
	}
	
	// DELETE
	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> deleteCharacterById(@PathVariable("id") long id) {

		characterService.delete(id);
		return ResponseEntity.accepted().build();
	}
	

}
