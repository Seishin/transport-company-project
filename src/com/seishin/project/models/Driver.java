package com.seishin.project.models;

import com.seishin.project.helpers.Gender;
import com.seishin.project.helpers.MaritalStatus;

public class Driver {
	
	private int id;
	private int age;
	private int truckId;
	private String truckRegNum;
	private String gender;
	private String maritialStatus;
	private String name;
	private String city;
	private String phoneNumber;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTruckId() {
		return truckId;
	}
	
	public void setTruckId(int id) {
		this.truckId = id;
	}
	
	public String getTruckRegNum() {
		return truckRegNum;
	}
	
	public void setTruckRegNum(String num) {
		this.truckRegNum = num;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender.equals(Gender.MALE) ? "Male" : "Female";
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getMaritialStatus() {
		return maritialStatus;
	}
	
	public void setMaritialStatus(MaritalStatus status) {
		this.maritialStatus = status.equals(MaritalStatus.SINGLE) ? "Single" : "Married";
	}
	
	public void setMaritialStatus(String status) {
		this.maritialStatus = status;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public String toString() {
		return String.format("Id: %d\n"
				+ "Name: %s\n"
				+ "Age: %d\n"
				+ "Gender: %s\n"
				+ "Maritial Status: %s\n"
				+ "City: %s\n"
				+ "Phone Number: %s\n"
				+ "Truck Id: %d", 
				this.id, this.name, this.age, this.gender, this.maritialStatus, this.city, this.phoneNumber, this.truckId);
				
	}
}
