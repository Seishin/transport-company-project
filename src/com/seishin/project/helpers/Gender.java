package com.seishin.project.helpers;

public enum Gender {
	MALE("Male"), FEMALE("Female");
	
	private String name;
	
	Gender(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
};
