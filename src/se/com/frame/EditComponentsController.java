package se.com.frame;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class EditComponentsController extends MainFrameController {

	public EditComponentsController(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		mainFrame.getBoard().addComponent(new BoardComponent(componentList.getSelectedValue(), e.getPoint()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		mainFrame.getRenderPanel().setAddingComponentPos(e.getPoint());
//		mainFrame.getRenderPanel().repaint();
	}

	@Override
	public JPanel getControllerPanel() {
		EditComponentsPanel panel = new EditComponentsPanel();
		
		return panel;
	}

	@Override
	public void finishController() {
		
	}

}
