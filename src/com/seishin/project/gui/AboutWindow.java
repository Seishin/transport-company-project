package com.seishin.project.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.seishin.project.Constants;

public class AboutWindow extends JFrame implements WindowListener{
	private static final long serialVersionUID = -5979263894366096557L;

	private static AboutWindow instance;
	
	public static AboutWindow getInstance() {
		if (instance == null) {
			instance = new AboutWindow();
		}
		
		return instance;
	}
	
	private AboutWindow() {
		initUI();
	}
	
	private void initUI() {
		setTitle("About");
		setMinimumSize(new Dimension(Constants.GUI_ADD_EDIT_WINDOW_WIDTH,
				Constants.GUI_ADD_EDIT_WINDOW_HEIGHT));
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		JLabel about = new JLabel("Created by:\n Atanas Dimitrov\n a.k.a\n Seishin", SwingConstants.CENTER);
		add(about);
	}
	
	public void showWindow() {
		pack();
		setVisible(true);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		instance = null;
	}
	
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
