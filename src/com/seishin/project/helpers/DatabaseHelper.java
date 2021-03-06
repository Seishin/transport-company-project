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
import com.seishin.project.models.Criteria;
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
		queryStr = "UPDATE " + Constants.DB_TABLE_DRIVERS + " SET "
				+ Constants.DRIVER_NAME + " = '" + driver.getName() + "', "
				+ Constants.DRIVER_AGE + " = '" + driver.getAge() + "', "
				+ Constants.DRIVER_GENDER + " = '" + driver.getGender() + "', "
				+ Constants.DRIVER_MARITIAL_STATUS + " = '" + driver.getMaritialStatus() + "', "
				+ Constants.DRIVER_CITY + " = '" + driver.getCity() + "', "
				+ Constants.DRIVER_PHONE_NUMBER + " = '" + driver.getPhoneNumber() + "', "
				+ Constants.DRIVER_TRUCK_ID + " = '" + driver.getTruckId() + "'"
				+ " WHERE " + Constants.ID + " = " + driver.getId() + ";";
		
		try {
			db.connect();
			
			preparedStatement = db.getConnection().prepareStatement(queryStr);
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
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
	
	public Driver getDriverByCriteria(Criteria criteria) {
		driver = new Driver();
		
		try {
			db.connect();
			
			queryStr = "SELECT " 
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.ID + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_NAME + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_AGE + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_GENDER + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_MARITIAL_STATUS + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_CITY + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_PHONE_NUMBER + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_TRUCK_ID + ", "
					+ Constants.DB_TABLE_TRUCKS + "." + Constants.TRUCK_REG_NUM + ", "
					+ Constants.DB_TABLE_TRUCKS + "." + Constants.ID
					+ " FROM " + Constants.DB_TABLE_DRIVERS  + ", " + Constants.DB_TABLE_TRUCKS
					+ " WHERE " + criteria.getKey() + " = '" + criteria.getValue()
					+ "' AND " + Constants.DB_TABLE_TRUCKS + "." + Constants.ID + " = " + Constants.DB_TABLE_DRIVERS + "." 
					+ Constants.DRIVER_TRUCK_ID;
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
				driver.setTruckRegNum(resultSet.getString(9));
			}
			
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return driver;
	}
	
	public ArrayList<Driver> getDriversByCriterias(ArrayList<Criteria> criterias) {
		drivers = new ArrayList<Driver>();
		
		if (criterias.isEmpty())
			return drivers;
		
		try {
			db.connect();
			
			queryStr = "SELECT "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.ID + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_NAME + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_AGE + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_GENDER + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_MARITIAL_STATUS + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_CITY + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_PHONE_NUMBER + ", "
					+ Constants.DB_TABLE_TRUCKS + "." + Constants.TRUCK_REG_NUM + ", "
					+ Constants.DB_TABLE_TRUCKS + "." + Constants.ID
					+ " FROM " + Constants.DB_TABLE_DRIVERS + ", " + Constants.DB_TABLE_TRUCKS
					+ " WHERE " + Constants.DB_TABLE_TRUCKS + "." + Constants.ID + " = " 
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_TRUCK_ID + " AND ";
			
			for (int i = 0; i < criterias.size(); i++) {
				if (i == criterias.size() - 1) {
					queryStr += criterias.get(i).getKey() + " LIKE '%" + criterias.get(i).getValue() + "%'";
				} else {
					queryStr += criterias.get(i).getKey() + " LIKE '%" + criterias.get(i).getValue() + "%' AND ";
				}
			}
			
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
				driver.setTruckRegNum(resultSet.getString(8));
				driver.setTruckId(resultSet.getInt(9));
				
				drivers.add(driver);
			}
			
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return drivers;
	}
	
	public ArrayList<Driver> getDrivers() {
		drivers = new ArrayList<Driver>();
		
		try {
			db.connect();
		
			queryStr = "SELECT " 
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.ID + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_NAME + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_AGE + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_GENDER + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_MARITIAL_STATUS + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_CITY + ", "
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_PHONE_NUMBER + ", "
					+ Constants.DB_TABLE_TRUCKS + "." + Constants.TRUCK_REG_NUM + ", "
					+ Constants.DB_TABLE_TRUCKS + "." + Constants.ID
					+ " FROM " + Constants.DB_TABLE_DRIVERS + ", " + Constants.DB_TABLE_TRUCKS
					+ " WHERE " + Constants.DB_TABLE_TRUCKS + "." + Constants.ID + " = " 
					+ Constants.DB_TABLE_DRIVERS + "." + Constants.DRIVER_TRUCK_ID;
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
				driver.setTruckRegNum(resultSet.getString(8));
				driver.setTruckId(resultSet.getInt(9));
				
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
			preparedStatement.setString(2, truck.getRegistrationNumber());
			preparedStatement.setString(3, truck.getFirstRegistration());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void updateTruck(Truck truck) {
		queryStr = "UPDATE " + Constants.DB_TABLE_TRUCKS + " SET "
				+ Constants.TRUCK_MAKE + " = '" + truck.getMake() + "', "
				+ Constants.TRUCK_FR + " = '" + truck.getFirstRegistration() + "', "
				+ Constants.TRUCK_REG_NUM + " = '" + truck.getRegistrationNumber() + "'"
				+ " WHERE " + Constants.ID + " = " + truck.getId() + ";";
		
		try {
			db.connect();
			
			preparedStatement = db.getConnection().prepareStatement(queryStr);
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void removeTruck(Truck truck) {
		queryStr = "DELETE FROM " + Constants.DB_TABLE_TRUCKS + " WHERE " + Constants.ID + " = " + truck.getId();
		
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
	
	public Truck getTruckByCriteria(Criteria criteria) {
		truck = new Truck();
		
		try {
			db.connect();
			
			queryStr = "SELECT * FROM " + Constants.DB_TABLE_TRUCKS + " WHERE " + criteria.getKey() + " = " + criteria.getValue();
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				truck.setId(resultSet.getInt(1));
				truck.setMake(resultSet.getString(2));
				truck.setRegistrationNumber(resultSet.getString(3));
				truck.setFirstRegistration(resultSet.getString(4));
			}
			
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return truck;
	}
	
	public ArrayList<Truck> getTrucks() {
		trucks = new ArrayList<Truck>();
		
		try {
			db.connect();
		
			queryStr = "SELECT * FROM " + Constants.DB_TABLE_TRUCKS;
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				truck = new Truck();
				
				truck.setId(resultSet.getInt(1));
				truck.setMake(resultSet.getString(2));
				truck.setRegistrationNumber(resultSet.getString(3));
				truck.setFirstRegistration(resultSet.getString(4));
				
				trucks.add(truck);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return trucks;
	}
	
	public ArrayList<Truck> getTrucksByCriterias(ArrayList<Criteria> criterias) {
		trucks = new ArrayList<Truck>();
		
		if (criterias.isEmpty())
			return trucks;
		
		try {
			db.connect();
			
			queryStr = "SELECT * FROM " + Constants.DB_TABLE_TRUCKS + " WHERE ";
			
			for (int i = 0; i < criterias.size(); i++) {
				if (i == criterias.size() - 1) {
					queryStr += criterias.get(i).getKey() + " LIKE '%" + criterias.get(i).getValue() + "%'";
				} else {
					queryStr += criterias.get(i).getKey() + " LIKE '%" + criterias.get(i).getValue() + "%' AND ";
				}
			}
			
			preparedStatement = db.getConnection().prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				truck = new Truck();
				
				truck.setId(resultSet.getInt(1));
				truck.setMake(resultSet.getString(2));
				truck.setRegistrationNumber(resultSet.getString(3));
				truck.setFirstRegistration(resultSet.getString(4));
				
				trucks.add(truck);
			}
			
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return trucks;
	}
}
