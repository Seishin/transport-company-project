package com.seishin.project.models;

public class Truck {
	
	private int id;
	private String make;
	private String registrationNumber;
	private String firstRegistration;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	
	public String getFirstRegistration() {
		return firstRegistration;
	}
	
	public void setFirstRegistration(String firstRegistration) {
		this.firstRegistration = firstRegistration;
	}
	
	@Override
	public String toString() {
		return String.format("Id: %d\n"
				+ "Make: %s\n"
				+ "Registration Number: %s\n"
				+ "First Registration: %s", this.id, this.make, this.registrationNumber, this.firstRegistration);
	}
}
