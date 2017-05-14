package se.com.frame;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import se.com.component.BoardComponent;
import se.com.frame.render.HighlightBox;
import se.com.util.ColorUtils;

public class EditComponentsController extends MainFrameController {

	private BoardComponent componentHighlighted;
	private HighlightBox componentHighlightedBox;
	private BoardComponent selectedComponent;
	private HighlightBox selectedComponentBox;
	private boolean movingComponent = false;
	
	public EditComponentsController(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		BoardComponent component = mainFrame.getBoard().getComponentAtPos(e.getPoint());
		if (component != null) {
			if (selectedComponent != component) {
				mainFrame.getRenderPanel().removeTemporaryDrawable(selectedComponentBox);
				
				selectedComponent = component;
				selectedComponentBox = new HighlightBox(selectedComponent.getBounds(), Color.GREEN);
				mainFrame.getRenderPanel().addTemporaryDrawable(selectedComponentBox);
			}
		} else {
			mainFrame.getRenderPanel().removeTemporaryDrawable(selectedComponentBox);
			
			selectedComponent = null;
			componentHighlighted = null;
			selectedComponentBox = null;
		}
		mainFrame.getRenderPanel().repaint();
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
			if (component != null) {
				if (componentHighlighted != component && component != selectedComponent) {
					mainFrame.getRenderPanel().removeTemporaryDrawable(componentHighlightedBox);
					
					componentHighlighted = component;
					componentHighlightedBox = new HighlightBox(componentHighlighted.getBounds(), ColorUtils.LIGHT_GREEN);
					mainFrame.getRenderPanel().addTemporaryDrawable(componentHighlightedBox);
				}
			} else {
				mainFrame.getRenderPanel().removeTemporaryDrawable(componentHighlightedBox);
				componentHighlighted = null;
				componentHighlightedBox = null;
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
