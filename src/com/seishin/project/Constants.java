package com.seishin.project;

public class Constants {
	public static final String DB_URL = "jdbc:h2:~/Workspace/University/Java/ProjectPracticum/db";
	public static final String DB_USERNAME = "sa";
	public static final String DB_PASSWORD = "";
	
	public static final String DB_TABLE_DRIVERS = "DRIVERS";
	public static final String DB_TABLE_TRUCKS = "TRUCKS";
	
	// Common Columns
	public static final String ID = "id";
	
	// Driver Table Columns
	public static final String DRIVER_NAME = "name";
	public static final String DRIVER_AGE = "age";
	public static final String DRIVER_GENDER = "gender";
	public static final String DRIVER_MARITIAL_STATUS = "maritial_status";
	public static final String DRIVER_CITY = "city";
	public static final String DRIVER_PHONE_NUMBER = "phone_number";
	public static final String DRIVER_TRUCK_ID = "truck_id";
	
	// Truck Table Columns
	public static final String TRUCK_MAKE = "make";
	public static final String TRUCK_REG_NUM = "registration_number";
	public static final String TRUCK_FR = "first_registration";
	
	// GUI
	public static final int GUI_WINDOW_WIDTH = 800;
	public static final int GUI_WINDOW_HEIGHT = 600;
	public static final int GUI_ADD_DRIVER_WINDOW_WIDTH = 400;
	public static final int GUI_ADD_DRIVER_WINDOW_HEIGHT = 300;
}
