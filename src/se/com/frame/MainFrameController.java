package se.com.frame;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public abstract class MainFrameController implements MouseInputListener {

	MainFrame mainFrame;
	
	public MainFrameController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public abstract JPanel getControllerPanel();
	
	public abstract void finishController();

}
