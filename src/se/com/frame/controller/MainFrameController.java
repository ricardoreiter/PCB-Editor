package se.com.frame.controller;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import se.com.frame.MainFrame;

public abstract class MainFrameController implements MouseInputListener {

	MainFrame mainFrame;
	
	public MainFrameController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public abstract JPanel getControllerPanel();
	
	public abstract void finishController();

}
