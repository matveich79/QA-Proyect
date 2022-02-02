package com.story.storyteller.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.story.storyteller.data.entity.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

	List<Character> findByName(String name);
	
	boolean existsByName(String name);
}
