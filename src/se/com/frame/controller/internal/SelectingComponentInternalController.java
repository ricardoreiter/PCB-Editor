package se.com.frame.controller.internal;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.BoardComponent;
import se.com.frame.MainFrame;
import se.com.frame.render.HighlightBox;
import se.com.util.ColorUtils;

public class SelectingComponentInternalController implements BoardEditorInternalController {

	private BoardComponent initialSelectedComponent;
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
	
	public SelectingComponentInternalController(MainFrame mainFrame, BoardEditorInternalControllerObserver observer, BoardComponent initialSelectedComponent) {
		this(mainFrame, observer);
		this.initialSelectedComponent = initialSelectedComponent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	private void selectComponent(BoardComponent component) {
		if (component != null) {
			if (selectedComponent != component) {
				mainFrame.getRenderPanel().removeTemporaryDrawable(selectedComponentBox);
				selectedComponent = component;
				
				selectedComponentBox = new HighlightBox(selectedComponent, Color.GREEN);
				mainFrame.getRenderPanel().addTemporaryDrawable(selectedComponentBox);
			}
		} else {
			unselectSelectedComponent();
		}
		observer.notify(this);
	}

	private void unselectSelectedComponent() {
		mainFrame.getRenderPanel().removeTemporaryDrawable(selectedComponentBox);
		
		selectedComponent = null;
		selectedComponentBox = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectComponent(mainFrame.getBoard().getComponentAtPos(e.getPoint()));
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
		BoardComponent component = mainFrame.getBoard().getComponentAtPos(e.getPoint());
		if (component != null) {
			if (componentHighlighted != component && component != selectedComponent) {
				mainFrame.getRenderPanel().removeTemporaryDrawable(componentHighlightedBox);
				
				componentHighlighted = component;
				componentHighlightedBox = new HighlightBox(componentHighlighted, ColorUtils.LIGHT_GREEN);
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
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			if (selectedComponent != null) {
				selectedComponent.rotate();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			if (selectedComponent != null) {
				selectedComponent.setParent(null);
				mainFrame.getBoard().removeComponent(selectedComponent);
				unselectSelectedComponent();
			}
		}
	}
	
	@Override
	public void startController() {
		selectComponent(initialSelectedComponent);
	}

	@Override
	public void finishController(boolean forced) {
		mainFrame.getRenderPanel().clearTemporaryDrawables();
	}
	
	public BoardComponent getSelectedComponent() {
		return selectedComponent;
	}

}
