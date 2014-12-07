package com.seishin.project;

import com.seishin.project.gui.MainScreen;
import com.seishin.project.helpers.DatabaseHelper;

public class Main {

	private static DatabaseHelper dbHelper;
	
	public static void main(String[] args) {
		dbHelper = DatabaseHelper.getInstance();
		
		MainScreen.getInstance().showScreen();
	}
}
