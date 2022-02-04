package com.story.storyteller.data.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "character_list")
public class Character {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	private Integer age;
	
	private String conflict;
	
	public Character() {
		super();
	}
	
	public Character(String name, String type, Integer age, String conflict) {
		super();
		this.name = name;
		this.type = type;
		this.age = age;
		this.conflict = conflict;
	}
	
	public Character(long id, String name, String type, Integer age, String conflict) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.age = age;
		this.conflict = conflict;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public String getConflict() {
		return conflict;
	}

	public void setConflict(String conflict) {
		this.conflict = conflict;
	}
	
	@Override
	public String toString() {
		return "Character [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", conflict=" + conflict + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, conflict, id, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		return Objects.equals(age, other.age) && Objects.equals(conflict, other.conflict)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}
	
}
