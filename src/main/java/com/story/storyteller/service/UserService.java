package com.story.storyteller.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.story.storyteller.data.entity.User;
import com.story.storyteller.data.repository.UserRepository;

//@Component
@Service
public class UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getById(Long id) {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id).get();
		}
		throw new EntityNotFoundException("User with id " + id + " does not exist.");
	}
	
	public User create(User user) {
		User savedUser = userRepository.save(user);
		return savedUser;
	}
	
	public User update(User user) {
		if (userRepository.existsById(id)) {
			User userInDb = userRepository.getById(id);
			userInDb.setAge(user.getAge());
			userInDb.setForename(user.getForename());
			userInDb.setSurname(user.getSurname());
			return userRepository.save(userInDb);
		} else {
			throw new EntityNotFoundException("User with id " + id + " does not exist.");
		}
	}
	
	public void delete(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		}
	}
	
}
