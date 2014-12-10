package com.seishin.project.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.seishin.project.Constants;
import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.helpers.Gender;
import com.seishin.project.helpers.MaritalStatus;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class DriverWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -7245908711646958984L;

	private static DriverWindow instance;

	private JPanel windowPanel;
	private JTextField nameField;
	private JTextField ageField;
	private JComboBox genderCombo;
	private JComboBox maritialStatusCombo;
	private JTextField cityField;
	private JTextField phoneNumber;
	private JComboBox truckCombo;

	private JPanel buttonsPanel;
	
	private JButton saveButton;
	private JButton cancelButton;
	private JButton deleteButton;
	
	private DatabaseHelper dbHelper;
	private Driver driver;
	private ArrayList<Truck> trucks;
	
	private boolean isEditing = false;
	
	public static DriverWindow getInstance() {
		if (instance == null) {
			instance = new DriverWindow();
		}

		return instance;
	}
	
	private DriverWindow() {
		dbHelper = DatabaseHelper.getInstance();

		initUI();
	}

	private void initUI() {
		setTitle("Add A New Driver");
		setMinimumSize(new Dimension(Constants.GUI_ADD_EDIT_WINDOW_WIDTH,
				Constants.GUI_ADD_EDIT_WINDOW_HEIGHT));
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		GridLayout layout = new GridLayout(7, 2, 10, 10);
		windowPanel = new JPanel();
		windowPanel.setLayout(layout);
		windowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		nameField = new JTextField();
		ageField = new JTextField();

		DefaultComboBoxModel genderModel = new DefaultComboBoxModel();
		genderModel.addElement("Male");
		genderModel.addElement("Female");
		genderCombo = new JComboBox(genderModel);

		DefaultComboBoxModel maritialStatusModel = new DefaultComboBoxModel();
		maritialStatusModel.addElement("Single");
		maritialStatusModel.addElement("Married");
		maritialStatusCombo = new JComboBox(maritialStatusModel);

		cityField = new JTextField();
		phoneNumber = new JTextField();

		truckCombo = new JComboBox(populateTrucksComboModel());

		windowPanel.add(new JLabel("Name: "));
		windowPanel.add(nameField);
		windowPanel.add(new JLabel("Age: "));
		windowPanel.add(ageField);
		windowPanel.add(new JLabel("Gender: "));
		windowPanel.add(genderCombo);
		windowPanel.add(new JLabel("Maritial Status: "));
		windowPanel.add(maritialStatusCombo);
		windowPanel.add(new JLabel("City: "));
		windowPanel.add(cityField);
		windowPanel.add(new JLabel("Phone Number: "));
		windowPanel.add(phoneNumber);
		windowPanel.add(new JLabel("Truck: "));
		windowPanel.add(truckCombo);

		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");

		buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(saveButton, BorderLayout.LINE_START);
		buttonsPanel.add(cancelButton, BorderLayout.LINE_END);

		add(windowPanel, BorderLayout.PAGE_START);
		add(buttonsPanel, BorderLayout.PAGE_END);

		// Listeners
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}
	
	public DriverWindow editDriver(Driver driver) {
		this.driver = driver;
		this.isEditing = true;
		setTitle("Editing Driver: " + driver.getName());
		
		nameField.setText(driver.getName());
		ageField.setText(String.valueOf(driver.getAge()));
		genderCombo.setSelectedIndex(driver.getGender().toLowerCase().equals("male") ? 0 : 1);
		maritialStatusCombo.setSelectedIndex(driver.getMaritialStatus().toLowerCase().equals("single") ? 0 : 1);
		cityField.setText(driver.getCity());
		phoneNumber.setText(driver.getPhoneNumber());
		truckCombo.setSelectedIndex(driver.getTruckId() - 1);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		buttonsPanel.add(deleteButton, BorderLayout.CENTER);
		
		return this;
	}

	private DefaultComboBoxModel populateTrucksComboModel() {
		trucks = dbHelper.getTrucks();

		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (Truck truck : trucks) {
			model.addElement(truck.getMake() + " - "
					+ truck.getRegistrationNumber());
		}

		return model;
	}

	private void saveData() {
		if (!isEditing) {
			driver = new Driver();
		}
		
		driver.setName(nameField.getText());
		driver.setAge(Integer.valueOf(ageField.getText()));
		driver.setGender(genderCombo.getSelectedItem().toString().toLowerCase()
				.equals("male") ? Gender.MALE : Gender.FEMALE);
		driver.setMaritialStatus(maritialStatusCombo.getSelectedItem()
				.toString().toLowerCase().equals("married") ? MaritalStatus.MARRIED
				: MaritalStatus.SINGLE);
		driver.setCity(cityField.getText());
		driver.setPhoneNumber(phoneNumber.getText());
		
		if (truckCombo.getItemCount() > 0) {
			driver.setTruckId(trucks.get(truckCombo.getSelectedIndex()).getId());
		}

		if (isEditing) {
			dbHelper.updateDriver(driver);
		} else {
			dbHelper.insertDriver(driver);
		}
		
		MainWindow.getInstance().refreshData();
		
		closeWindow();
	}

	private boolean isDataValid() {
		boolean isValid = true;

		if (nameField.getText().length() == 0
				|| nameField.getText().matches(".*\\d.*")) {
			nameField.setText("Empty or contains digits!");
			isValid = false;
		}

		if (ageField.getText().length() == 0
				|| !ageField.getText().matches(".*\\d.*")) {
			ageField.setText("Empty or non digit symbols!");
			isValid = false;
		}

		if (cityField.getText().length() == 0
				|| cityField.getText().matches(".*\\d.*")) {
			cityField.setText("Empty or contains digits!");
			isValid = false;
		}

		if (phoneNumber.getText().length() == 0
				|| !phoneNumber.getText().matches(".*\\d.*")) {
			phoneNumber.setText("Empty or non digit symbols!");
			isValid = false;
		}

		return isValid;
	}

	public void showWindow() {
		pack();
		setVisible(true);
	}
	
	public void closeWindow() {
		instance = null;
		dispatchEvent(new WindowEvent(this, Event.WINDOW_DESTROY));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(saveButton)) {
			if (isDataValid()) {
				saveData();
			}
		}

		if (e.getSource().equals(cancelButton)) {
			closeWindow();
		}
		
		if (e.getSource().equals(deleteButton)) {
			dbHelper.removeDriver(driver);
			MainWindow.getInstance().refreshData();
			closeWindow();
		}
	}
}
