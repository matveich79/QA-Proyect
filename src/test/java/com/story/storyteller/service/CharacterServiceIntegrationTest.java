package com.story.storyteller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;
import com.story.storyteller.service.CharacterService;


@SpringBootTest
@Transactional
public class CharacterServiceIntegrationTest {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private CharacterRepository characterRepository;

	private List<Character> charactersInDatabase;
	private long nextNewElementsId;
	
	// initialise the database for each test as it is rolled back afterwards
	@BeforeEach
	public void init() {
		List<Character> characters = new ArrayList<>();
		characters.addAll(List.of(new Character(1, "Marta", "Hero", 16, "Quest"),
	   			  				  new Character(2, "Anna", "Ally", 17, "recognition"),
	   			  				  new Character(3, "Peter", "Sadow", 20, "romance")));
		charactersInDatabase = new ArrayList<>();
		charactersInDatabase.addAll(characterRepository.saveAll(characters));
		int size = charactersInDatabase.size();
		nextNewElementsId = charactersInDatabase.get(size - 1).getId() + 1;
	}
	
	@Test
	public void getAllCharactersTest() {
		assertThat(charactersInDatabase).isEqualTo(characterService.getAll());
	}
	
	@Test
	public void createCharacterTest() {
		Character characterToSave = new Character("Jon", "Guardian", 8, "gift");
		Character expectedCharacter = new Character(nextNewElementsId, characterToSave.getName(), characterToSave.getType(), characterToSave.getAge(), characterToSave.getConflict());
		
		assertThat(expectedCharacter).isEqualTo(characterService.create(characterToSave));
	}
	
	@Test
	public void getCharacterByIdTest() {
		Character characterInDb = charactersInDatabase.get(0);
		assertThat(characterService.getById(characterInDb.getId())).isEqualTo(characterInDb);
	}

	@Test
	public void updateCharacterTest() {
		Character characterInDb = charactersInDatabase.get(0);
		long id = characterInDb.getId();
		Character userWithUpdatesToMake = new Character(characterInDb.getId(), 
											  characterInDb.getName(), 
											  characterInDb.getType(), 
											  characterInDb.getAge() + 1,
											  characterInDb.getConflict());
		
		Character actual = characterService.update(id, userWithUpdatesToMake);
		assertThat(actual).isEqualTo(userWithUpdatesToMake);
	}

	@Test
	public void deleteCharacterTest() {
		Character characterInDb = charactersInDatabase.get(0);
		long id = characterInDb.getId();
		
		characterService.delete(id);
		
		assertThat(characterRepository.findById(id)).isEqualTo(Optional.empty());
	}
}
