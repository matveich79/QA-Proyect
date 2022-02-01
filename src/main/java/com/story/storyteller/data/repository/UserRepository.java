package com.story.storyteller.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.story.storyteller.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
