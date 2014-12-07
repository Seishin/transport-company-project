package com.seishin.project.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.seishin.project.Constants;
import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class MainScreen extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5817148016540674552L;

	private static MainScreen instance;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu newMenu;
	private JMenu helpMenu;
	private JMenuItem addDriverMenuItem;
	private JMenuItem addTruckMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem exitMenuItem;
	
	private JTabbedPane tabbedPane;
	private JComponent driversPane;
	private JComponent trucksPane;
	
	private JTable driversTable;
	private JTable trucksTable;
	
	private ArrayList<Driver> drivers;
	private ArrayList<Truck> trucks;
	
	private DatabaseHelper dbHelper;

	public static MainScreen getInstance() {
		if (instance == null) {
			instance = new MainScreen();
		}

		return instance;
	}

	public MainScreen() {
		dbHelper = DatabaseHelper.getInstance();

		this.drivers = dbHelper.getDrivers();
		this.trucks = dbHelper.getTrucks();
		
		initUI();
	}

	private void initUI() {
		setTitle("Transportation Company");
		setMinimumSize(new Dimension(Constants.GUI_WINDOW_WIDTH,
				Constants.GUI_WINDOW_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();
		initTabbedPane();
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();
		
		// Initialization
		fileMenu = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");
		
		newMenu = new JMenu("New");
		addDriverMenuItem = new JMenuItem("Add Driver");
		addTruckMenuItem = new JMenuItem("Add Truck");
		
		helpMenu = new JMenu("Help");
		aboutMenuItem = new JMenuItem("About");

		// Adding to the JMenu
		menuBar.add(fileMenu);
		menuBar.add(newMenu);
		menuBar.add(helpMenu);
		
		fileMenu.add(exitMenuItem);

		newMenu.add(addDriverMenuItem);
		newMenu.add(addTruckMenuItem);
		
		helpMenu.add(aboutMenuItem);
		
		// Listeners
		exitMenuItem.addActionListener(this);
		addDriverMenuItem.addActionListener(this);
		addTruckMenuItem.addActionListener(this);

		setJMenuBar(menuBar);
	}

	private void initTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Drivers", initDriverPanel());
		tabbedPane.add("Trucks", initTrucksPanel());
		
		add(tabbedPane);
	}
	
	private JComponent initDriverPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));
		
		driversTable = new JTable(populateDriversData(), getDriverColumnNamesVector());
		driversTable.setFillsViewportHeight(true);
		driversTable.setGridColor(Color.BLACK);
		
		JScrollPane scrollPane = new JScrollPane(driversTable);
		
		panel.add(scrollPane);
		
		return panel;
	}
	
	private JComponent initTrucksPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));
		
		trucksTable = new JTable(populateTrucksData(), getTrucksColumnNamesVector());
		trucksTable.setFillsViewportHeight(true);
		trucksTable.setGridColor(Color.BLACK);
		
		JScrollPane scrollPane = new JScrollPane(trucksTable);
		
		panel.add(scrollPane);
		
		return panel;
	}
	
	private Vector<String> getDriverColumnNamesVector() {
		Vector<String> columns = new Vector<String>();
		columns.add("ID");
		columns.add("Name");
		columns.add("Age");
		columns.add("Gender");
		columns.add("Maritial Status");
		columns.add("City");
		columns.add("Phone Number");
		columns.add("Truck Registration Number");
		
		return columns;
	}
	
	private Vector<String> getTrucksColumnNamesVector() {
		Vector<String> columns = new Vector<String>();
		columns.add("ID");
		columns.add("Make");
		columns.add("Registration Number");
		columns.add("First Registration Date");
		
		return columns;
	}
	
	private Vector<Vector<String>> populateDriversData() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		
		for (Driver driver : drivers) {
			Vector<String> driverV = new Vector<String>();
			driverV.add(String.valueOf(driver.getId()));
			driverV.add(driver.getName());
			driverV.add(String.valueOf(driver.getAge()));
			driverV.add(driver.getGender());
			driverV.add(driver.getMaritialStatus());
			driverV.add(driver.getCity());
			driverV.add(driver.getPhoneNumber());
			driverV.add(String.valueOf(driver.getTruckId()));
			
			data.add(driverV);
		}
 		
		return data;
	}
	
	private Vector<Vector<String>> populateTrucksData() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		
		for (Truck truck : trucks) {
			Vector<String> truckV = new Vector<String>();
			truckV.add(String.valueOf(truck.getId()));
			truckV.add(truck.getMake());
			truckV.add(truck.getRegistrationNumber());
			truckV.add(truck.getFirstRegistration());
			
			data.add(truckV);
		}
 		
		return data;
	}
	
	public void refreshData() {
		drivers.clear();
		drivers = dbHelper.getDrivers();
		
		trucks.clear();
		trucks = dbHelper.getTrucks();
		
		((DefaultTableModel) driversTable.getModel()).setRowCount(0);
		((DefaultTableModel) trucksTable.getModel()).setNumRows(0);
		
		((DefaultTableModel) driversTable.getModel()).setDataVector(populateDriversData(), getDriverColumnNamesVector());
		((DefaultTableModel) trucksTable.getModel()).setDataVector(populateTrucksData(), getTrucksColumnNamesVector());
		
		driversTable.updateUI();
		trucksTable.updateUI();
	}
	
	public void showScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitMenuItem)) {
			System.exit(0);
		}
		
		if (e.getSource().equals(addDriverMenuItem)) {
			AddDriverScreen.getInstance().showScreen();
		}
		
		if (e.getSource().equals(addTruckMenuItem)) {
			
		}
		
		if (e.getSource().equals(aboutMenuItem)) {
			
		}
	}
}
