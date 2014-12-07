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

import com.seishin.project.Constants;
import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.helpers.Gender;
import com.seishin.project.helpers.MaritalStatus;
import com.seishin.project.models.Driver;
import com.seishin.project.models.Truck;

public class AddDriverScreen extends JFrame implements ActionListener {
	private static final long serialVersionUID = -7245908711646958984L;

	private static AddDriverScreen instance;

	private JPanel windowPanel;
	private JTextField nameField;
	private JTextField ageField;
	private JComboBox genderCombo;
	private JComboBox maritialStatusCombo;
	private JTextField cityField;
	private JTextField phoneNumber;
	private JComboBox truckCombo;

	private JButton saveButton;
	private JButton cancelButton;
	
	private DatabaseHelper dbHelper;

	public static AddDriverScreen getInstance() {
		if (instance == null) {
			instance = new AddDriverScreen();
		}

		return instance;
	}

	private AddDriverScreen() {
		dbHelper = DatabaseHelper.getInstance();

		initUI();
	}

	private void initUI() {
		setTitle("Add A New Driver");
		setMinimumSize(new Dimension(Constants.GUI_ADD_DRIVER_WINDOW_WIDTH,
				Constants.GUI_ADD_DRIVER_WINDOW_HEIGHT));
		setLayout(new BorderLayout());

		windowPanel = new JPanel();
		GridLayout layout = new GridLayout(7, 2, 10, 10);
		windowPanel.setLayout(layout);

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

		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(saveButton, BorderLayout.LINE_START);
		buttonsPanel.add(cancelButton, BorderLayout.LINE_END);

		add(windowPanel, BorderLayout.PAGE_START);
		add(buttonsPanel, BorderLayout.PAGE_END);

		// Listeners
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	private DefaultComboBoxModel populateTrucksComboModel() {
		ArrayList<Truck> trucks = dbHelper.getTrucks();

		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (Truck truck : trucks) {
			model.addElement(truck.getMake() + " - "
					+ truck.getRegistrationNumber());
		}

		return model;
	}

	private void saveData() {
		Driver driver = new Driver();
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
			driver.setTruckId(truckCombo.getSelectedIndex() + 1);
		}

		dbHelper.insertDriver(driver);
		
		MainScreen.getInstance().refreshData();
		
		closeScreen();
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

	private void clearFields() {
		nameField.setText(null);
		ageField.setText(null);
		genderCombo.setSelectedIndex(0);
		maritialStatusCombo.setSelectedIndex(0);
		cityField.setText(null);
		phoneNumber.setText(null);
		truckCombo.setSelectedIndex(0);
	}

	public void showScreen() {
		pack();
		setVisible(true);
	}
	
	public void closeScreen() {
		clearFields();
		dispose();
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
			closeScreen();
		}
	}
}
