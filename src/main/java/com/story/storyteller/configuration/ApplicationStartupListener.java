package com.story.storyteller.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.story.storyteller.data.entity.Character;
import com.story.storyteller.data.repository.CharacterRepository;

@Profile("dev")
@Configuration
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private CharacterRepository characterRepository;

	@Autowired
	public ApplicationStartupListener(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		characterRepository.saveAll(List.of(
				new Character("Sam", "Hero", 28, "romance"),
				new Character("Anna", "Ally", 31, "quest"),
				new Character("Paul", "Mentor", 39, "recognition")
				));
	}
}
