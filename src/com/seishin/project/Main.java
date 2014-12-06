package com.seishin.project;

import java.util.ArrayList;

import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.helpers.Gender;
import com.seishin.project.helpers.MaritalStatus;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class Main {

	public static void main(String[] args) {
//		Driver driver = new Driver();
//		driver.setName("Mark");
//		driver.setAge(42);
//		driver.setGender(Gender.MALE);
//		driver.setMaritialStatus(MaritalStatus.MARRIED);
//		driver.setCity("Plovdiv");
//		driver.setPhoneNumber("321312321");
//		driver.setTruckId(1);
//		
//		Truck truck = new Truck();
//		truck.setMake("Iveco");
//		truck.setFirstRegistration("11/2011");
//		truck.setRegistrationNumber("PB1242BC");
		
		DatabaseHelper dbHelper = DatabaseHelper.getInstance();
		Driver driver = dbHelper.getDriverById(1);
		dbHelper.removeDriver(driver);
		
//		dbHelper.insertTruck(truck);
//		dbHelper.insertDriver(driver);
//		Driver driver = dbHelper.getDriverById(1);
		
//		ArrayList<Driver> drivers = dbHelper.getDrivers();
//		
//		for (Driver driver : drivers) {
//			System.out.println(driver.toString());
//		}
		
		
//		System.out.println(truck.toString());
	}
}
