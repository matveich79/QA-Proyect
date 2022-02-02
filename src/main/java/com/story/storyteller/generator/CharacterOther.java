package com.story.storyteller.generator;

public class CharacterOther {
	
	private int id;
	private String name;
	private String type;
	private String background;
	private String conflict;
	private String desire;
	
	
	public CharacterOther(int id, String name, String type, String background, String conflict, String desire) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.background = background;
		this.conflict = conflict;
		this.desire = desire;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getConflict() {
		return conflict;
	}
	public void setConflict(String conflict) {
		this.conflict = conflict;
	}
	public String getDesire() {
		return desire;
	}
	public void setDesire(String desire) {
		this.desire = desire;
	}
	
	
	
}
