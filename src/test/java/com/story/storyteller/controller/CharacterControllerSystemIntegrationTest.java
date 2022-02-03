package com.story.storyteller.controller;

import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.user_app.data.entity.User;
import com.qa.user_app.data.repository.UserRepository;
import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CharacterControllerSystemIntegrationTest {

	// MockMvc sends http requests
	@Autowired
	private MockMvc mockMvc;

	// ObjectMappers serializes and deserializes objects
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CharacterRepository characterRepository;

	private List<Character> charactersInDatabase;
	private long nextNewElementsId;
	
	// Initialize the database for our tests
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
	public void getAllCharactersTest() throws Exception {
		// Create a mock http request builder
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/character");

		// Specify the Accept header for the expected returned content type (MediaType)
		mockRequest.accept(MediaType.APPLICATION_JSON); // Accept: application/json

		// Create expected JSON String from usersInDatabase using the ObjectMapper
		String characters = objectMapper.writeValueAsString(charactersInDatabase);

		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(characters);

		// Assert results
		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

	@Test
	public void createCharacterTest() throws Exception {
		Character characterToSave = new Character("Jon", "Guardian", 8, "gift");
		Character expectedCharacter = new Character(nextNewElementsId, characterToSave.getName(), characterToSave.getType(),
				characterToSave.getAge(), characterToSave.getConflict());

		// Configure mock request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/character");

		mockRequest.contentType(MediaType.APPLICATION_JSON); 
																
		mockRequest.content(objectMapper.writeValueAsString(characterToSave));

		// body
		mockRequest.accept(MediaType.APPLICATION_JSON);

		// Configure ResultMatchers
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content()
				.json(objectMapper.writeValueAsString(expectedCharacter));

		// Send the request and assert the results where as expected
		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

	@Test
	public void getCharacterByIdTest() throws Exception {
		Character expectedCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/character/id/1");
		
		mockRequest.accept(MediaType.APPLICATION_JSON);
		
		String characterJson = objectMapper.writeValueAsString(expectedCharacter);
		
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(characterJson);
		
		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}
	
	@Test
	public void getCharacterByNameTest() throws Exception {
		Character expectedCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/character/name/Marta");
		
		mockRequest.accept(MediaType.APPLICATION_JSON);
		
		String characterJson = objectMapper.writeValueAsString(expectedCharacter);
		
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(characterJson);
		
		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

	@Test
	public void updateCharacterTest() throws Exception {
		// Given
		Long characterId = 2L;
		Character detailsToUpdate = new Character("Jon", "Guardian", 8, "gift");
		Character expectedCharacter = new Character(characterId, "Jon", "Guardian", 8, "gift");
		// Configure mock request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/character/id/" + characterId);
		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(objectMapper.writeValueAsString(detailsToUpdate));
		mockRequest.accept(MediaType.APPLICATION_JSON);
		String characterJson = objectMapper.writeValueAsString(expectedCharacter);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(characterJson);
																						
		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

//	public void deteleCharacterByIdTest() throws Exception {
//		List<Character> charactersLeft = new ArrayList<>();
//		charactersLeft.addAll(List.of(new Character(1, "Marta", "Hero", 16, "Quest"),
//	   			  				  new Character(3, "Peter", "Sadow", 20, "romance")));
//		
//		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE, "/character/id/2");
//		
//		mockRequest.accept(MediaType.APPLICATION_JSON);
//		
//		String characterJson = objectMapper.writeValueAsString(charactersLeft);
//		
//		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();
//		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(characterJson);
//		
//		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
//	}
}
