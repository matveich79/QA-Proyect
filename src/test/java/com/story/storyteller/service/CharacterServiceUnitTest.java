package com.story.storyteller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;
import com.story.storyteller.exceptions.CharacterNotFoundException;
import com.story.storyteller.service.CharacterService;

//@ExtendWith(MockitoExtension.class) 
public class CharacterServiceUnitTest {

	@Mock
	private CharacterRepository characterRepository;

	@InjectMocks 
	private CharacterService characterService;

	private List<Character> characters;
	private Character expectedCharacterWithId;
	private Character expectedCharacterWithoutId;

	@BeforeEach 
	public void init() {
		characters = new ArrayList<>();
		characters.addAll(List.of(new Character(1, "Marta", "Hero", 16, "Quest"),
	   			  				  new Character(2, "Anna", "Ally", 17, "recognition"),
	   			  				  new Character(3, "Peter", "Sadow", 20, "romance")));
		expectedCharacterWithoutId = new Character("Marta", "Hero", 16, "Quest");
		expectedCharacterWithId = new Character(1, "Marta", "Hero", 16, "Quest");
	}

	@AfterEach
    public void deleteDataAfterEachTest() {
        characterRepository.deleteAll();
    }
	@Test
	public void getAllCharactersTest() {
		when(characterRepository.findAll()).thenReturn(characters);
		assertThat(characterService.getAll()).isEqualTo(characters);
		verify(characterRepository).findAll();
	}

	@Test
	public void createCharacterTest() {
		when(characterRepository.save(expectedCharacterWithoutId)).thenReturn(expectedCharacterWithId);

		assertThat(characterService.create(expectedCharacterWithoutId)).isEqualTo(expectedCharacterWithId);
		verify(characterRepository).save(expectedCharacterWithoutId);
	}

	@Test
	public void getCharacterByIdTest() {
		long id = expectedCharacterWithId.getId();
		when(characterRepository.findById(id)).thenReturn(Optional.of(expectedCharacterWithId));
		assertThat(characterService.getById(id)).isEqualTo(expectedCharacterWithId);
		verify(characterRepository).findById(id);
	}
	
	@Test
	public void getCharacterByInvalidIdTest() {

		long id = 76;
		when(characterRepository.findById(id)).thenReturn(Optional.empty());
		

		CharacterNotFoundException e = Assertions.assertThrows(CharacterNotFoundException.class, () -> {
			characterService.getById(id);
		});
		
		String expected = "Character with id " + id + " does not exist";
		assertThat(e.getMessage()).isEqualTo(expected);
		verify(characterRepository).findById(id);
	}
	
	@Test
	public void getCharacterByInvalidNameTest() {

		String name = "saldgjibasfgkljasbf";
		List<Character> emptyList = new ArrayList<>();
		when(characterRepository.findByName(name)).thenReturn(emptyList);
		
		CharacterNotFoundException e = Assertions.assertThrows(CharacterNotFoundException.class, () -> {
			characterService.getByName(name);
		});
		
		String expected = "Character with name " + name + " does not exist";
		assertThat(e.getMessage()).isEqualTo(expected);
		verify(characterRepository).findByName(name);
	}

	@Test
	public void updateCharacterTest() {
		long id = expectedCharacterWithId.getId();
		Character characterWithUpdatesToMake = new Character(expectedCharacterWithId.getId(),
											  expectedCharacterWithId.getName(), 
											  expectedCharacterWithId.getType(), 
											  expectedCharacterWithId.getAge() + 1,
											  expectedCharacterWithId.getConflict());
		
		when(characterRepository.existsById(id)).thenReturn(true);
		when(characterRepository.getById(id)).thenReturn(expectedCharacterWithId);
		when(characterRepository.save(expectedCharacterWithId)).thenReturn(characterWithUpdatesToMake);
		
		assertThat(characterService.update(id, characterWithUpdatesToMake)).isEqualTo(characterWithUpdatesToMake);
		verify(characterRepository).existsById(id);
		verify(characterRepository).getById(id);
		verify(characterRepository).save(expectedCharacterWithId);
	}

	@Test
	public void deleteCharacterTest() {
		long id = expectedCharacterWithId.getId();
		when(characterRepository.existsById(id)).thenReturn(true);
		characterService.delete(id);
		verify(characterRepository).existsById(id);
		verify(characterRepository).deleteById(id);
	}

}
