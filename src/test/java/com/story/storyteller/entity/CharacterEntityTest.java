package com.story.storyteller.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.story.storyteller.data.entity.Character;

@SpringBootTest
@Transactional
public class CharacterEntityTest {
	
	@Test
	public void toStringTest() {
		Character dummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		String expectedString = "Character [id=1, name=Marta, type=Hero, age=16, conflict=Quest]";
		
		assertThat(dummyCharacter.toString()).isEqualTo(expectedString);
	}
	
	@Test
	public void hashCodeTest() {
		Character dummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		
		assertThat(dummyCharacter.hashCode()).isEqualTo(Objects.hash(16, "Quest", 1, "Marta", "Hero"));
	}
	
	@Test
	public void equalsThisTest() {
		Character dummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		assertThat(dummyCharacter.equals(dummyCharacter)).isEqualTo(true);
	}
	
	@Test
	public void equalsNullTest() {
		Character dummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		assertThat(dummyCharacter.equals(null)).isEqualTo(false);
	}
	
	@Test
	public void equalsClassTest() {
		Character dummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		String otherClass = "Other class";
		assertThat(dummyCharacter.equals(otherClass)).isEqualTo(false);
	}
	
	@Test
	public void equalsOtherTest() {
		Character dummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		Character otherDummyCharacter = new Character(1, "Marta", "Hero", 16, "Quest");
		assertThat(dummyCharacter.equals(otherDummyCharacter)).isEqualTo(true);
	}
}
