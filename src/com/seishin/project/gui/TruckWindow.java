package com.seishin.project.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class TruckWindow extends JFrame implements ActionListener {

	private static TruckWindow instance;
	
	public static TruckWindow getInstance() {
		if (instance == null) {
			instance = new TruckWindow();
		}
		
		return instance;
	}
	
	private TruckWindow() {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
