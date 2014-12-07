package com.seishin.project.gui;

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

import com.seishin.project.Constants;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class MainScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5817148016540674552L;

	private static MainScreen instance;

	private JFrame window;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu addDriverMenu;
	private JMenu addTruckMenu;
	private JMenu aboutMenu;
	private JMenuItem exitMenuItem;
	
	private JTabbedPane tabbedPane;
	private JComponent driversPane;
	private JComponent trucksPane;
	
	private JTable driversTable;
	private JTable trucksTable;
	
	private ArrayList<Driver> drivers;
	private ArrayList<Truck> trucks;

	public static MainScreen getInstance(ArrayList<Driver> drivers, ArrayList<Truck> trucks) {
		if (instance == null) {
			instance = new MainScreen(drivers, trucks);
		}

		return instance;
	}

	public MainScreen(ArrayList<Driver> drivers, ArrayList<Truck> trucks) {
		super(new GridLayout(1, 1));
		
		this.drivers = drivers;
		this.trucks = trucks;
		
		initUI();
	}

	private void initUI() {
		window = new JFrame("Transportation Company");
		window.setMinimumSize(new Dimension(Constants.GUI_WINDOW_WIDTH,
				Constants.GUI_WINDOW_HEIGHT));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();
		initTabbedPane();
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();
		
		// Initialization
		fileMenu = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");
		
		addDriverMenu = new JMenu("Add Driver");
		addTruckMenu = new JMenu("Add Truck");
		aboutMenu = new JMenu("About");

		// Adding to the JMenu
		menuBar.add(fileMenu);
		menuBar.add(addDriverMenu);
		menuBar.add(addTruckMenu);
		menuBar.add(aboutMenu);
		
		fileMenu.add(exitMenuItem);
		
		// Listeners
		exitMenuItem.addActionListener(this);
		addDriverMenu.addActionListener(this);
		addTruckMenu.addActionListener(this);

		window.setJMenuBar(menuBar);
	}

	private void initTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Drivers", initDriverPanel());
		tabbedPane.add("Trucks", initTrucksPanel());
		
		window.add(tabbedPane);
	}
	
	private JComponent initDriverPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));
		
		driversTable = new JTable(populateDriversData(), getDriverColumnNamesVector());
		driversTable.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(driversTable);
		
		panel.add(scrollPane);
		
		return panel;
	}
	
	private JComponent initTrucksPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));
		
		trucksTable = new JTable(populateTrucksData(), getTrucksColumnNamesVector());
		
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
			truckV.add(truck.getFirstRegistration());
			truckV.add(truck.getRegistrationNumber());
			
			data.add(truckV);
		}
 		
		return data;
	}
	
	public void launchScreen() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitMenuItem)) {
			System.exit(0);
		}
		
		if (e.getSource().equals(addDriverMenu)) {
			
		}
		
		if (e.getSource().equals(addTruckMenu)) {
			
		}
		
		if (e.getSource().equals(aboutMenu)) {
			
		}
	}
}
