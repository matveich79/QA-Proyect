package com.story.storyteller.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.story.storyteller.service.CharacterService;
import com.story.storyteller.data.entity.Character;

//@SpringBootTest
@WebMvcTest(CharacterController.class)
public class CharacterControllerWebIntegrationTest {

	@Autowired
	private CharacterController controller;
	
	@MockBean
	private CharacterService characterService;
	
	private List<Character> characters;
	private Character characterToCreate;
	private Character validCharacter;
	private List<Character> validByName;
	
	@BeforeEach
	public void init() {
		characters = new ArrayList<>();
		characters.addAll(List.of(new Character(1, "Jon", "Hero", 20, "quest"),
					   			  new Character(2, "Anna", "Mentor", 23, "recognition"),
					   			  new Character(3, "Kevin", "Ally", 32, "romance")));
		characterToCreate = new Character("Jon", "Hero", 20, "quest");
		validCharacter = new Character(1, "Jon", "Hero", 20, "quest");
		validByName = new ArrayList<>();
		validByName.add(new Character(2, "Anna", "Mentor", 23, "recognition"));
	}
	
	@Test // JUnit
	public void getAllCharactersTest() {
		// Given
		ResponseEntity<List<Character>> expected = new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
		
		// When
		when(characterService.getAll()).thenReturn(characters);
		
		// Then
		ResponseEntity<List<Character>> actual = controller.getCharacters();
		assertThat(expected).isEqualTo(actual);
		
		// verify service was called by controller
		verify(characterService, times(1)).getAll();
		
	}
	
	@Test
	public void createCharacterTest() {
		// Given
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/character/" + String.valueOf(validCharacter.getId()));
		ResponseEntity<Character> expected = new ResponseEntity<Character>(validCharacter, headers, HttpStatus.CREATED);
		
		// When
		when(characterService.create(characterToCreate)).thenReturn(validCharacter);
		
		// Then
		ResponseEntity<Character> actual = controller.createCharacter(characterToCreate);
		assertEquals(expected, actual);
		// assertThat(expected).isEqualTo(actual);
		
		// Verify
		verify(characterService).create(characterToCreate);
	}
	
	@Test
	public void getCharacterByIdTest() {
		ResponseEntity<Character> expected = ResponseEntity.of(Optional.of(validCharacter));
		
		when(characterService.getById(1L)).thenReturn(validCharacter);
		
		ResponseEntity<Character> actual = controller.getCharacterById(1L);
		
		assertEquals(expected, actual);
		
		verify(characterService, times(1)).getById(1L);
	}
	
	@Test
	public void getCharacterByNameTest() {
		//ResponseEntity<Character> expected = ResponseEntity.of(Optional.of(validCharacter));
		ResponseEntity<List<Character>> expected = new ResponseEntity<List<Character>>(validByName, HttpStatus.OK);
		
		when(characterService.getByName("Anna")).thenReturn(validByName);
		
		ResponseEntity<List<Character>> actual = controller.getCharacterByName("Anna");
		
		assertThat(expected).isEqualTo(actual);
		
		verify(characterService, times(1)).getByName("Anna");
		
		
	}
	
	@Test
	public void updateCharacterTest() {
		Character updatedCharacter = new Character(1, "Peter", "shadow", 12, "recognition");
		Character toUpdateWith = new Character("Peter", "shadow", 12, "recognition");
		long characterId = updatedCharacter.getId();
		
		// Given
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/character/id/" + String.valueOf(characterId));
		ResponseEntity<Character> expected = new ResponseEntity<Character>(updatedCharacter, headers, HttpStatus.ACCEPTED);
		
		// When
		when(characterService.update(characterId, toUpdateWith)).thenReturn(updatedCharacter);
		
		ResponseEntity<Character> actual = controller.updateCharacter(characterId, toUpdateWith);
		
		// Then
		assertEquals(expected, actual);
		
		// Verify
		verify(characterService).update(characterId, toUpdateWith);
	}
	
	@Test
	public void deleteCharacterTest() {
		long characterId = 1;
		ResponseEntity<?> expected = ResponseEntity.accepted().build();
		ResponseEntity<?> actual = controller.deleteCharacterById(characterId);
		
		assertEquals(expected, actual);
		verify(characterService).delete(characterId);
	}
}
