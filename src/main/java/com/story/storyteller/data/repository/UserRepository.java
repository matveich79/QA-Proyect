package com.story.storyteller.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.story.storyteller.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
