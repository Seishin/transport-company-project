package com.seishin.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.seishin.project.Constants;
import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.helpers.Gender;
import com.seishin.project.helpers.MaritalStatus;
import com.seishin.project.models.Criteria;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5817148016540674552L;

	private static MainWindow instance;

	private JPanel driversFilterPanel;
	private JTextField criteriaTextField;
	private JComboBox criteriaCombo;
	private JComboBox genderCombo;
	private JComboBox maritialStatusCombo;
	private JLabel genderLabel;
	private JLabel maritialStatusLabel;
	private JButton driversFilterButton;
	private JButton resetDriversButton;
	
	private JPanel trucksFilterPanel;
	private JTextField truckMakeTextField;
	private JTextField truckRegNumTextField;
	private JComboBox truckFRMonthCombo;
	private JComboBox truckFRYearCombo;
	private JButton trucksFilterButton;
	private JButton trucksResetButton;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu helpMenu;
	private JMenuItem addDriverMenuItem;
	private JMenuItem addTruckMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem exitMenuItem;

	private JTabbedPane tabbedPane;
	private JTable driversTable;
	private JTable trucksTable;

	private ArrayList<Driver> drivers;
	private ArrayList<Truck> trucks;
	
	private ArrayList<Criteria> filterCriterias = new ArrayList<Criteria>();

	private DatabaseHelper dbHelper;

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}

		return instance;
	}

	public MainWindow() {
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
		setLocationRelativeTo(null);

		initMenuBar();
		initTabbedPane();
		initDriversFilterPanel();
		initTrucksFilterPanel();
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();

		// Initialization
		fileMenu = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");

		editMenu = new JMenu("Edit");
		addDriverMenuItem = new JMenuItem("Add Driver");
		addTruckMenuItem = new JMenuItem("Add Truck");
		
		helpMenu = new JMenu("Help");
		aboutMenuItem = new JMenuItem("About");

		// Adding to the JMenu
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);

		fileMenu.add(exitMenuItem);

		editMenu.add(addDriverMenuItem);
		editMenu.add(addTruckMenuItem);

		helpMenu.add(aboutMenuItem);

		// Listeners
		exitMenuItem.addActionListener(this);
		addDriverMenuItem.addActionListener(this);
		addTruckMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);

		setJMenuBar(menuBar);
	}
	
	private void initDriversFilterPanel() {
		driversFilterPanel = new JPanel();
		driversFilterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Drivers Filter"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		driversFilterPanel.setLayout(new BorderLayout());
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		criteriaTextField = new JTextField();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.7;
		c.gridx = 0;
		c.gridy = 0;
		filtersPanel.add(criteriaTextField, c);

		DefaultComboBoxModel criteriasModel = new DefaultComboBoxModel();
		criteriasModel.addElement("Unspecified");
		criteriasModel.addElement("Name");
		criteriasModel.addElement("Age");
		criteriasModel.addElement("City");
		criteriasModel.addElement("Truck Registration");
		criteriasModel.addElement("Phone Number");
		
		criteriaCombo = new JComboBox(criteriasModel);
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 1;
		c.gridy = 0;
		filtersPanel.add(criteriaCombo, c);
		
		genderLabel = new JLabel("Gender:");
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.7;
		c.gridx = 0;
		c.gridy = 2;
		filtersPanel.add(genderLabel, c);
		
		DefaultComboBoxModel genderModel = new DefaultComboBoxModel();
		genderModel.addElement("Unspecified");
		genderModel.addElement("Male");
		genderModel.addElement("Female");
		
		genderCombo = new JComboBox(genderModel);
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 1;
		c.gridy = 2;
		filtersPanel.add(genderCombo, c);
		
		maritialStatusLabel = new JLabel("Maritial Status:");
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.7;
		c.gridx = 0;
		c.gridy = 3;
		filtersPanel.add(maritialStatusLabel, c);
		
		DefaultComboBoxModel maritialStatusModel = new DefaultComboBoxModel();
		maritialStatusModel.addElement("Unspecified");
		maritialStatusModel.addElement("Single");
		maritialStatusModel.addElement("Married");
		
		maritialStatusCombo = new JComboBox(maritialStatusModel);
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 1;
		c.gridy = 3;
		filtersPanel.add(maritialStatusCombo, c);
		
		driversFilterPanel.add(filtersPanel);
		
		JPanel buttons = new JPanel();
		
		driversFilterButton = new JButton("Filter");
		driversFilterButton.addActionListener(this);
		buttons.add(driversFilterButton);
		
		resetDriversButton = new JButton("Reset");
		resetDriversButton.addActionListener(this);
		buttons.add(resetDriversButton);
		
		driversFilterPanel.add(buttons, BorderLayout.SOUTH);
		
		add(driversFilterPanel, BorderLayout.NORTH);
	}
	
	private void initTrucksFilterPanel() {
		trucksFilterPanel = new JPanel();
		trucksFilterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Trucks Filter"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		trucksFilterPanel.setLayout(new BorderLayout());
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel truckMakeLabel = new JLabel("Make");
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.6;
		c.gridx = 0;
		c.gridy = 0;
		filtersPanel.add(truckMakeLabel, c);
		
		truckMakeTextField = new JTextField();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.4;
		c.gridx = 1;
		c.gridy = 0;
		filtersPanel.add(truckMakeTextField, c);
		
		JLabel truckRegNumLabel = new JLabel("Registration Number:");
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.6;
		c.gridx = 0;
		c.gridy = 1;
		filtersPanel.add(truckRegNumLabel, c);
		
		truckRegNumTextField = new JTextField();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.4;
		c.gridx = 1;
		c.gridy = 1;
		filtersPanel.add(truckRegNumTextField, c);
		
		JLabel truckFRLabel = new JLabel("First Registration:");
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.6;
		c.gridx = 0;
		c.gridy = 2;
		filtersPanel.add(truckFRLabel, c);
		
		JPanel truckFRPanel = new JPanel(new GridBagLayout());
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.4;
		c.gridx = 1;
		c.gridy = 2;
		filtersPanel.add(truckFRPanel, c);
		
		DefaultComboBoxModel truckFRMonthModel = new DefaultComboBoxModel();
		truckFRMonthModel.addElement("Month");
		for (int i = 1; i <= 12; i++) {
			truckFRMonthModel.addElement(String.valueOf(i));
		}
		
		truckFRMonthCombo = new JComboBox(truckFRMonthModel);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.4;
		c.gridx = 0;
		c.gridy = 0;
		truckFRPanel.add(truckFRMonthCombo, c);
		
		JLabel truckFRSeparator = new JLabel("/");
		truckFRSeparator.setHorizontalAlignment(JLabel.CENTER);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.gridx = 1;
		c.gridy = 0;
		truckFRPanel.add(truckFRSeparator, c);
		
		DefaultComboBoxModel truckFRYearModel = new DefaultComboBoxModel();
		truckFRYearModel.addElement("Year");
		for (int i = 1980; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
			truckFRYearModel.addElement(String.valueOf(i));
		}
		
		truckFRYearCombo = new JComboBox(truckFRYearModel);
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.4;
		c.gridx = 3;
		c.gridy = 0;
		truckFRPanel.add(truckFRYearCombo, c);
		
		trucksFilterPanel.add(filtersPanel);
		
		JPanel buttons = new JPanel();
		
		trucksFilterButton = new JButton("Filter");
		trucksFilterButton.addActionListener(this);
		buttons.add(trucksFilterButton);
		
		trucksResetButton = new JButton("Reset");
		trucksResetButton.addActionListener(this);
		buttons.add(trucksResetButton);
		
		trucksFilterPanel.add(buttons, BorderLayout.SOUTH);
	}

	private void initTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Drivers", initDriverPanel());
		tabbedPane.add("Trucks", initTrucksPanel());
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					remove(trucksFilterPanel);
					add(driversFilterPanel);
					repaint();
				} else if (tabbedPane.getSelectedIndex() == 1) {
					remove(driversFilterPanel);
					add(trucksFilterPanel);
					repaint();
				}
			}
		});

		add(tabbedPane, BorderLayout.SOUTH);
	}

	private JComponent initDriverPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));

		driversTableModel.setDataVector(populateDriversData(),
				getDriverColumnNamesVector());
		
		driversTable = new JTable(driversTableModel);
		driversTable.setFillsViewportHeight(true);
		driversTable.setGridColor(Color.BLACK);
		
		driversTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					DriverWindow.getInstance()
					.editDriver(drivers.get(driversTable.getSelectedRow()))
					.showWindow();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(driversTable);

		panel.add(scrollPane);

		return panel;
	}

	private JComponent initTrucksPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, 1));

		trucksTableModel.setDataVector(populateTrucksData(),
				getTrucksColumnNamesVector());
		
		trucksTable = new JTable(trucksTableModel);
		trucksTable.setFillsViewportHeight(true);
		trucksTable.setGridColor(Color.BLACK);
		
		trucksTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					TruckWindow.getInstance()
					.editTruck(trucks.get(trucksTable.getSelectedRow()))
					.showWindow();
				}
			}
		});
		
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
			driverV.add(driver.getTruckRegNum());

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
	
	private void filterDrivers() {
		filterCriterias.clear();
		
		if (criteriaTextField.getText().length() > 0 && criteriaCombo.getSelectedIndex() != 0) {
			String selectedCriteria = null;
			
			if (criteriaCombo.getSelectedIndex() == 1) {
				selectedCriteria = Constants.DRIVER_NAME;
			} else if (criteriaCombo.getSelectedIndex() == 2) {
				selectedCriteria = Constants.DRIVER_AGE;
			} else if (criteriaCombo.getSelectedIndex() == 3) {
				selectedCriteria = Constants.DRIVER_CITY;
			} else if (criteriaCombo.getSelectedIndex() == 4) {
				selectedCriteria = Constants.TRUCK_REG_NUM;
			} else if (criteriaCombo.getSelectedIndex() == 5) {
				selectedCriteria = Constants.DRIVER_PHONE_NUMBER;
			}
			
			filterCriterias.add(new Criteria(selectedCriteria, criteriaTextField.getText()));
		}
		
		if (genderCombo.getSelectedIndex() != 0) {
			if (genderCombo.getSelectedIndex() == 1) {
				filterCriterias.add(new Criteria(Constants.DRIVER_GENDER, Gender.MALE.toString()));
			} else if (genderCombo.getSelectedIndex() == 2) {
				filterCriterias.add(new Criteria(Constants.DRIVER_GENDER, Gender.FEMALE.toString()));
			}
		}
		
		if (maritialStatusCombo.getSelectedIndex() != 0) {
			if (maritialStatusCombo.getSelectedIndex() == 1) {
				filterCriterias.add(new Criteria(Constants.DRIVER_MARITIAL_STATUS, MaritalStatus.SINGLE.toString()));
			} else if (maritialStatusCombo.getSelectedIndex() == 2) {
				filterCriterias.add(new Criteria(Constants.DRIVER_MARITIAL_STATUS, MaritalStatus.MARRIED.toString()));
			}
		}
		
		drivers.clear();
		drivers = dbHelper.getDriversByCriterias(filterCriterias);
		
		refreshDriversTableAfterFilter();
	}
	
	private void filterTrucks() {
		filterCriterias.clear();
		
		if (truckMakeTextField.getText().length() > 0) {
			filterCriterias.add(new Criteria(Constants.TRUCK_MAKE, truckMakeTextField.getText()));
		}
		
		if (truckRegNumTextField.getText().length() > 0) {
			filterCriterias.add(new Criteria(Constants.TRUCK_REG_NUM, truckRegNumTextField.getText()));
		}
		
		String truckFR = "";
		
		if (truckFRMonthCombo.getSelectedIndex() > 0) {
			truckFR = truckFRMonthCombo.getSelectedItem().toString();
		}
		
		if (truckFRYearCombo.getSelectedIndex() > 0) {
			if (truckFR.length() > 0) {
				truckFR += "/" + truckFRYearCombo.getSelectedItem().toString();
			} else {
				truckFR = truckFRYearCombo.getSelectedItem().toString();
			}
		}
		
		filterCriterias.add(new Criteria(Constants.TRUCK_FR, truckFR));
		
		trucks.clear();
		trucks = dbHelper.getTrucksByCriterias(filterCriterias);
		
		refreshTrucksTableAfterFilter();
	}

	public void refreshData() {
		drivers.clear();
		drivers = dbHelper.getDrivers();

		trucks.clear();
		trucks = dbHelper.getTrucks();

		((DefaultTableModel) driversTable.getModel()).setRowCount(0);
		((DefaultTableModel) trucksTable.getModel()).setNumRows(0);

		((DefaultTableModel) driversTable.getModel()).setDataVector(
				populateDriversData(), getDriverColumnNamesVector());
		((DefaultTableModel) trucksTable.getModel()).setDataVector(
				populateTrucksData(), getTrucksColumnNamesVector());

		driversTable.updateUI();
		trucksTable.updateUI();
	}
	
	private void refreshDriversTableAfterFilter() {
		((DefaultTableModel) driversTable.getModel()).setRowCount(0);

		((DefaultTableModel) driversTable.getModel()).setDataVector(
				populateDriversData(), getDriverColumnNamesVector());

		driversTable.updateUI();
	}
	
	private void refreshTrucksTableAfterFilter() {
		((DefaultTableModel) trucksTable.getModel()).setNumRows(0);
		
		((DefaultTableModel) trucksTable.getModel()).setDataVector(
				populateTrucksData(), getTrucksColumnNamesVector());
		
		trucksTable.updateUI();
	}
	
	private void resetDriversFilterFields() {
		criteriaTextField.setText("");
		criteriaCombo.setSelectedIndex(0);
		
		genderCombo.setSelectedIndex(0);
		maritialStatusCombo.setSelectedIndex(0);
	}
	
	private void resetTrucksFilterFields() {
		truckMakeTextField.setText("");
		truckRegNumTextField.setText("");
		truckFRMonthCombo.setSelectedIndex(0);
		truckFRYearCombo.setSelectedIndex(0);
	}

	public void showWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	DefaultTableModel driversTableModel = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		@Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	
	DefaultTableModel trucksTableModel = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		@Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitMenuItem)) {
			System.exit(0);
		}

		if (e.getSource().equals(addDriverMenuItem)) {
			DriverWindow.getInstance().showWindow();
		}

		if (e.getSource().equals(addTruckMenuItem)) {
			TruckWindow.getInstance().showWindow();
		}

		if (e.getSource().equals(aboutMenuItem)) {
			AboutWindow.getInstance().showWindow();
		}
		
		if (e.getSource().equals(driversFilterButton)) {
			filterDrivers();
		}
		
		if (e.getSource().equals(resetDriversButton)) {
			refreshData();
			resetDriversFilterFields();
		}
		
		if (e.getSource().equals(trucksFilterButton)) {
			filterTrucks();
		}
		 
		if (e.getSource().equals(trucksResetButton)) {
			refreshData();
			resetTrucksFilterFields();
		}
	}
}
