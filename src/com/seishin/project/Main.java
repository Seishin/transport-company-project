package com.seishin.project;

import java.util.ArrayList;

import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.helpers.Gender;
import com.seishin.project.helpers.MaritalStatus;
import com.seishin.project.models.Criteria;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class Main {

	public static void main(String[] args) {
//		Driver driver = new Driver();
//		driver.setName("Ivan");
//		driver.setAge(42);
//		driver.setGender(Gender.MALE);
//		driver.setMaritialStatus(MaritalStatus.MARRIED);
//		driver.setCity("Plovdiv");
//		driver.setPhoneNumber("321312321");
//		driver.setTruckId(1);
//		
//		Truck truck = new Truck();
//		truck.setMake("Volvo");
//		truck.setFirstRegistration("11/2011");
//		truck.setRegistrationNumber("PB1232BC");
		
		DatabaseHelper dbHelper = DatabaseHelper.getInstance();
//		ArrayList<Criteria> criterias = new ArrayList<Criteria>();
//		criterias.add(new Criteria(Constants.DRIVER_PHONE_NUMBER, "321312321"));
//		criterias.add(new Criteria(Constants.DRIVER_CITY, "Plovdiv"));
//		Driver driver = dbHelper.getDriverByCriterias(criterias);
		
//		dbHelper.insertTruck(truck);
//		dbHelper.insertDriver(driver);
//		Driver driver = dbHelper.getDriverById(1);
//		driver.setAge(32);
//		dbHelper.updateDriver(driver);
		
//		ArrayList<Driver> drivers = dbHelper.getDriversByCriterias(criterias);
//		
//		for (Driver driver : drivers) {
//			System.out.println(driver.toString());
//		}
//		
//		Truck truck = dbHelper.getTruckById(1);
//		truck.setMake("Scania");
//		dbHelper.updateTruck(truck);
//		
//		truck = dbHelper.getTruckById(1);
//		System.out.println(truck.toString());
		
		ArrayList<Criteria> truckCriterias = new ArrayList<Criteria>();
		truckCriterias.add(new Criteria(Constants.TRUCK_FR, "11/2011"));
		truckCriterias.add(new Criteria(Constants.TRUCK_REG_NUM, "PB1242BC"));
		
		ArrayList<Truck> trucks = dbHelper.getTrucksByCriterias(truckCriterias);
		for (Truck truck : trucks) {
			System.out.println(truck.toString());
		}
	}
}
