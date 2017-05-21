package se.com.frame.controller.internal;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.Board;
import se.com.component.BoardComponent;
import se.com.component.ComponentConfig;
import se.com.frame.MainFrame;

public class AddComponentInternalController implements BoardEditorInternalController {

	private BoardComponent component;
	private BoardComponent lastAddedComponent;
	private BoardEditorInternalControllerObserver observer;
	private MainFrame mainFrame;
	
	public AddComponentInternalController(ComponentConfig component, BoardEditorInternalControllerObserver observer, MainFrame mainFrame) {
		this.component = new BoardComponent(component, null);
		this.observer = observer;
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle globalBounds = component.getGlobalBounds();
		Board board = mainFrame.getBoard();
		if (board.isInsideWorkableArea(globalBounds) && !board.intersectsWithComponent(component)) {
			lastAddedComponent = new BoardComponent(component, board);
			lastAddedComponent.setGlobalPos(e.getPoint());
			board.addComponent(lastAddedComponent);
			if (observer != null)
				observer.notify(this);
		}
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
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			component.rotate();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			lastAddedComponent = null;
			if (observer != null)
				observer.notify(this);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		component.setGlobalPos(e.getPoint());
	}
	
	@Override
	public void startController() {
		mainFrame.getRenderPanel().addTemporaryDrawable(this.component);
	}

	@Override
	public void finishController(boolean forced) {
		mainFrame.getRenderPanel().clearTemporaryDrawables();
	}

	public BoardComponent getLastAddedComponent() {
		return lastAddedComponent;
	}
	
}
