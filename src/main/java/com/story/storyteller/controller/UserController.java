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

import com.story.storyteller.data.entity.User;
import com.story.storyteller.data.repository.UserRepository;
import com.story.storyteller.service.UserService;


@RestController // this is a bean that should be stored in the app context
@RequestMapping(path = "/user") // access this controller at localhost:8080/user
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	//private static long counter = 1;

	//private List<User> users = new ArrayList<>(List.of(new User(counter++, "Fred", "Daly", 33), new User(counter++, "Sarah", "Smith", 29)));
	
	// READ ALL
	@GetMapping // localhost:8080/user
	public ResponseEntity<List<User>> getUsers() {
		ResponseEntity<List<User>> users = ResponseEntity.ok(userService.getAll());
		return users;
	}
	
	
	// READ BY ID
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
//		for (User user : users) {
//			if (user.getId() == id) {
//				return user;
//			}
//		}
//		throw new EntityNotFoundException("Entity with id " + id + " was not found.");
		User savedUser = userService.getById(id);
		
		ResponseEntity<User> response = ResponseEntity.status(HttpStatus.OK)
													  .body(savedUser);
		return response;
	}
	
	// CREATE
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
//		System.out.println(user);
//		user.setId(counter++);
//		users.add(user);
//		return user;
		
		User savedUser = userService.create(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/user/" + String.valueOf(savedUser.getId()));
		headers.add("Contest-Type", "application/json");
		
		ResponseEntity<User> response = new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
		return response;
	}
	
	//UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
//		if (userExists(id)) {
//			for (User userInDb : users) {
//				if (userInDb.getId() == id) {
//					userInDb.setAge(user.getAge());
//					userInDb.setForename(user.getForename());
//					userInDb.setSurname(user.getSurname());
//					return userInDb;
//				}
//			}
//		}
		User updatedUser = userService.update(id, user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/user/" + String.valueOf(updatedUser.getId()));
		
		return new ResponseEntity<User>(updatedUser, headers, HttpStatus.ACCEPTED);
	}
	
	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
//		if (userExists(id)) {
//			Iterator<User> iterator = users.iterator();
//			while (iterator.hasNext()) {
//				User user = iterator.next();
//				if (user.getId() == id) {
//					iterator.remove();
//					return;
//				}
//			}
//		} else {
//			throw new EntityNotFoundException("Entity with id " + id + " was not found.");
//		}
		userService.delete(id);
		return ResponseEntity.accepted().build();
	}
	
//	private boolean userExists(long id) {
//		for (User user : users) {
//			if (user.getId() == id) {
//				return true;
//			}
//		}
//		return false;
//	}
}
