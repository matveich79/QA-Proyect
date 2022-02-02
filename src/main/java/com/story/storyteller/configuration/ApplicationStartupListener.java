package com.story.storyteller.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.story.storyteller.data.entity.User;
import com.story.storyteller.data.repository.UserRepository;

@Configuration
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private UserRepository userRepository;

	@Autowired
	public ApplicationStartupListener(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		userRepository.saveAll(List.of(
				new User("Jon", "Smith", 28),
				new User("Anna", "Jones", 31),
				new User("Paul", "Evans", 39)
				));
	}
}
