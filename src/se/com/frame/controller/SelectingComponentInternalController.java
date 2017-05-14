package se.com.frame.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.BoardComponent;
import se.com.frame.MainFrame;
import se.com.frame.render.HighlightBox;
import se.com.util.ColorUtils;

public class SelectingComponentInternalController implements BoardEditorInternalController {

	private BoardComponent componentHighlighted;
	private HighlightBox componentHighlightedBox;
	private BoardComponent selectedComponent;
	private HighlightBox selectedComponentBox;
	private BoardEditorInternalControllerObserver observer;
	private MainFrame mainFrame;
	
	public SelectingComponentInternalController(MainFrame frame, BoardEditorInternalControllerObserver observer) {
		super();
		this.mainFrame = frame;
		this.observer = observer;
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
		observer.controllerFinished(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishController(boolean forced) {
		mainFrame.getRenderPanel().clearTemporaryDrawables();
	}
	
	public BoardComponent getSelectedComponent() {
		return selectedComponent;
	}

}
