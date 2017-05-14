package se.com.frame.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.BoardComponent;

public class MovingComponentInternalController implements BoardEditorInternalController {

	private BoardComponent component;
	private Point movingComponentInitialPos;
	private int movingComponentInitialRotation;
	private BoardEditorInternalControllerObserver observer;
	
	public MovingComponentInternalController(BoardComponent component, BoardEditorInternalControllerObserver observer) {
		this.component = component;
		this.movingComponentInitialPos = (Point) component.getPos().clone();
		this.movingComponentInitialRotation = component.getRotation();
		this.observer = observer;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		component.setPos(e.getPoint());
		if (observer != null)
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
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			component.setRotation(component.getRotation() + 90);
		}
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		component.setPos(e.getPoint());
	}

	@Override
	public void finishController(boolean forced) {
		if (forced) {
			component.setPos(movingComponentInitialPos);
			component.setRotation(movingComponentInitialRotation);
		}
	}

}
