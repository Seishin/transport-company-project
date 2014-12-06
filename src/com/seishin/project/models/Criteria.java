package com.seishin.project.models;

public class Criteria {

	private String key;
	private String value;
	
	public Criteria(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public Criteria(String key, int value) {
		this.key = key;
		this.value = String.valueOf(value);
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValue(int value) {
		this.value = String.valueOf(value);
	}
}
