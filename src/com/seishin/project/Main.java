package com.seishin.project;

import java.util.ArrayList;

import com.seishin.project.gui.MainScreen;
import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class Main {

	private static DatabaseHelper dbHelper;
	
	public static void main(String[] args) {
		dbHelper = DatabaseHelper.getInstance();
		
		MainScreen.getInstance(loadDriversData(), loadTrucksData()).launchScreen();
	}
	
	private static ArrayList<Driver> loadDriversData() {
		return dbHelper.getDrivers();
	}
	
	private static ArrayList<Truck> loadTrucksData() {
		return dbHelper.getTrucks();
	}
}
