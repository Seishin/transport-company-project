package com.seishin.project.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.seishin.project.Constants;
import com.seishin.project.helpers.DatabaseHelper;
import com.seishin.project.models.Truck;

public class TruckWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -335316921595248416L;

	private static TruckWindow instance;
	
	private JPanel windowPanel;
	private JTextField makeField;
	private JTextField registrationNumberField;
	private JTextField firstRegistrationField;
	
	private JPanel buttonsPanel;
	
	private JButton saveButton;
	private JButton cancelButton;
	private JButton deleteButton;
	
	private DatabaseHelper dbHelper;
	private Truck truck;
	
	private boolean isEditing = false;
	
	public static TruckWindow getInstance() {
		if (instance == null) {
			instance = new TruckWindow();
		}
		
		return instance;
	}
	
	private TruckWindow() {
		dbHelper = DatabaseHelper.getInstance();
		
		initUI();
	}
	
	private void initUI() {
		setTitle("Add A New Truck");
		setMinimumSize(new Dimension(Constants.GUI_ADD_EDIT_WINDOW_WIDTH,
				Constants.GUI_ADD_EDIT_WINDOW_HEIGHT));
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		GridLayout layout = new GridLayout(3, 2, 10, 10);
		windowPanel = new JPanel();
		windowPanel.setLayout(layout);
		windowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		makeField = new JTextField();
		registrationNumberField = new JTextField();
		firstRegistrationField = new JTextField();
		
		windowPanel.add(new JLabel("Truck Make: "));
		windowPanel.add(makeField);
		windowPanel.add(new JLabel("Registration Number: "));
		windowPanel.add(registrationNumberField);
		windowPanel.add(new JLabel("First Registration Date: "));
		windowPanel.add(firstRegistrationField);
		
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
	
	private boolean isDataValid() {
		boolean isValid = true;
		
		if (makeField.getText().length() == 0) {
			makeField.setText("Empty field!");
		}
		
		if (registrationNumberField.getText().length() == 0) {
			registrationNumberField.setText("Empty field!");
		}
		
		if (firstRegistrationField.getText().length() == 0) {
			firstRegistrationField.setText("Empty field!");
		}
		
		return isValid;
	}
	
	public TruckWindow editTruck(Truck truck) {
		this.truck = truck;
		this.isEditing = true;
		setTitle("Editing Truck With Registration Number: " + truck.getRegistrationNumber());
		
		makeField.setText(truck.getMake());
		registrationNumberField.setText(truck.getRegistrationNumber());
		firstRegistrationField.setText(truck.getFirstRegistration());
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		buttonsPanel.add(deleteButton, BorderLayout.CENTER);
		
		return this;
	}
	
	private void saveData() {
		if (!isEditing) {
			truck = new Truck();
		}
		
		truck.setMake(makeField.getText());
		truck.setRegistrationNumber(registrationNumberField.getText());
		truck.setFirstRegistration(firstRegistrationField.getText());
		
		if (isEditing) {
			dbHelper.updateTruck(truck);
		} else {
			dbHelper.insertTruck(truck);
		}
		
		MainWindow.getInstance().refreshData();
		
		closeWindow();
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
			dbHelper.removeTruck(truck);
			MainWindow.getInstance().refreshData();
			closeWindow();
		}
	}

}
