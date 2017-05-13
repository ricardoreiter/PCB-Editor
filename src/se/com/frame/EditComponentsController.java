package se.com.frame;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import se.com.component.BoardComponent;

public class EditComponentsController extends MainFrameController {

	private BoardComponent selectedComponent;
	private boolean movingComponent = false;
	
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
		if (!movingComponent) {
			BoardComponent component = mainFrame.getBoard().getComponentAtPos(e.getPoint());
			if (component != null && selectedComponent != component) {
				selectedComponent = component;
				System.out.println(component.getConfig());
			}
		}
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
