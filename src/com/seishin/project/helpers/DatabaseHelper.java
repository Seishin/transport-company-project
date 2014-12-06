package com.seishin.project.helpers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.seishin.project.Constants;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class DatabaseHelper {
	
	private static DatabaseHelper instance;
	private Database db;
	
	private String queryStr;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	private Driver driver;
	private ArrayList<Driver> drivers;
	private Truck truck;
	private ArrayList<Truck> trucks;
	
	public static DatabaseHelper getInstance() {
		if (instance == null) {
			instance = new DatabaseHelper();
		}
		
		return instance;
	}
	
	private DatabaseHelper() {
		this.db = new Database();
	}
	
	private static class Database {
		
		private Connection connection;
		private String queryStr;
		private Statement statement;
		
		public Database() {
			checkIfDBExists();
		}
		
		private boolean checkIfTrucksTableExists() {
			try {
				DatabaseMetaData dbm = connection.getMetaData();
				ResultSet table = dbm.getTables(null, null, Constants.DB_TABLE_TRUCKS, null);
				
				if (table.next()) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
		}
		
		private boolean checkIfDriversTableExists() {
			try {
				DatabaseMetaData dbm = connection.getMetaData();
				ResultSet table = dbm.getTables(null, null, Constants.DB_TABLE_DRIVERS, null);
				
				if (table.next()) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
		}
		
		public void checkIfDBExists() {
			connect();
			
			if (!checkIfTrucksTableExists()) {
				createTrucksTable();
			} 
			
			if (!checkIfDriversTableExists()) {
				createDriversTable();
			}
			
			close();
		}
		
		public void createDriversTable() {
			queryStr = "CREATE TABLE " + Constants.DB_TABLE_DRIVERS + "("
					+ Constants.ID + " int auto_increment,"
					+ Constants.DRIVER_NAME + " nvarchar(20) not null,"
					+ Constants.DRIVER_AGE + " int not null,"
					+ Constants.DRIVER_GENDER + " nvarchar(20) not null,"
					+ Constants.DRIVER_MARITIAL_STATUS + " nvarchar(20) not null,"
					+ Constants.DRIVER_CITY + " nvarchar(20) not null,"
					+ Constants.DRIVER_PHONE_NUMBER + " nvarchar(20) not null,"
					+ Constants.DRIVER_TRUCK_ID + " int null,"
					+ " primary key (" + Constants.ID + "), "
					+ " foreign key (" + Constants.DRIVER_TRUCK_ID + ") references " 
					+ Constants.DB_TABLE_TRUCKS + " (" + Constants.ID + "))";
			
			try {
				statement = connection.createStatement();
				statement.execute(queryStr);
				
				System.out.println(String.format("Table %s was succcessfully created!", Constants.DB_TABLE_DRIVERS));
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		public void createTrucksTable() {
			queryStr = "CREATE TABLE " + Constants.DB_TABLE_TRUCKS + "("
					+ Constants.ID + " int auto_increment,"
					+ Constants.TRUCK_MAKE + " nvarchar(20) not null,"
					+ Constants.TRUCK_REG_NUM + " nvarchar(20) not null,"
					+ Constants.TRUCK_FR + " nvarchar(20) not null,"
					+ " primary key (" + Constants.ID + "))";
			
			try {
				statement = connection.createStatement();
				statement.execute(queryStr);
				
				System.out.println(String.format("Table %s was succcessfully created!", Constants.DB_TABLE_TRUCKS));
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		protected void connect() {
			try {
				Class.forName("org.h2.Driver");
				this.connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		protected void close() {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		protected Connection getConnection() {
			return connection;
		}
	}
	
	public void insertDriver(Driver driver) {
		queryStr = "INSERT INTO " + Constants.DB_TABLE_DRIVERS + " VALUES(null, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			db.connect();
			
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, driver.getName());
			preparedStatement.setInt(2, driver.getAge());
			preparedStatement.setString(3, driver.getGender());
			preparedStatement.setString(4, driver.getMaritialStatus());
			preparedStatement.setString(5, driver.getCity());
			preparedStatement.setString(6, driver.getPhoneNumber());
			preparedStatement.setInt(7, driver.getTruckId());
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void updateDriver(Driver driver) {
		
	}
	
	public void removeDriver(Driver driver) {
		queryStr = "DELETE FROM " + Constants.DB_TABLE_DRIVERS + " WHERE " + Constants.ID + " = " + driver.getId();
		
		try {
			db.connect();
			
			preparedStatement = db.getConnection().prepareStatement(queryStr);
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public Driver getDriverById(int id) {
		driver = new Driver();
		
		try {
			db.connect();
			
			queryStr = "SELECT * FROM " + Constants.DB_TABLE_DRIVERS + " WHERE " + Constants.ID + " = " + id;
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				driver.setId(resultSet.getInt(1));
				driver.setName(resultSet.getString(2));
				driver.setAge(resultSet.getInt(3));
				driver.setGender(resultSet.getString(4));
				driver.setMaritialStatus(resultSet.getString(5));
				driver.setCity(resultSet.getString(6));
				driver.setPhoneNumber(resultSet.getString(7));
				driver.setTruckId(resultSet.getInt(8));
			}
			
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return driver;
	}
	
	public ArrayList<Driver> getDrivers() {
		drivers = new ArrayList<Driver>();
		
		try {
			db.connect();
		
			queryStr = "SELECT * FROM " + Constants.DB_TABLE_DRIVERS;
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				driver = new Driver();
				driver.setId(resultSet.getInt(1));
				driver.setName(resultSet.getString(2));
				driver.setAge(resultSet.getInt(3));
				driver.setGender(resultSet.getString(4));
				driver.setMaritialStatus(resultSet.getString(5));
				driver.setCity(resultSet.getString(6));
				driver.setPhoneNumber(resultSet.getString(7));
				driver.setTruckId(resultSet.getInt(8));
				
				drivers.add(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return drivers;
	}
	
	public void insertTruck(Truck truck) {
		queryStr = "INSERT INTO " + Constants.DB_TABLE_TRUCKS + " VALUES(null, ?, ?, ?)";
		
		try {
			db.connect();
			
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, truck.getMake());
			preparedStatement.setString(3, truck.getRegistrationNumber());
			preparedStatement.setString(2, truck.getFirstRegistration());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void updateTruck(Truck truck) {
		
	}
	
	public void removeTruck(Truck truck) {
		
	}
	
	public Truck getTruckById(int id) {
		return new Truck();
	}
	
	public ArrayList<Truck> getTrucks() {
		return new ArrayList<Truck>();
	}
}
