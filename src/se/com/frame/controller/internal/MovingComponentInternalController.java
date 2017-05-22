package se.com.frame.controller.internal;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.Board;
import se.com.component.BoardComponent;

/**
 * Sub-controller that controls the position of a component on the board.
 * Use the mouseDragged event to change its position.
 */
public class MovingComponentInternalController implements BoardEditorInternalController {

	private BoardComponent component;
	private Point movingComponentInitialPos;
	private int movingComponentInitialRotation;
	private BoardEditorInternalControllerObserver observer;
	private Point oldPoint;
	private Board board;
	
	public MovingComponentInternalController(BoardComponent component, BoardEditorInternalControllerObserver observer, Board board) {
		this.component = component;
		this.movingComponentInitialPos = (Point) component.getPos().clone();
		this.movingComponentInitialRotation = component.getRotation();
		this.observer = observer;
		this.board = board;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Rectangle globalBounds = component.getGlobalBounds();
		if (!board.isInsideWorkableArea(globalBounds) || board.intersectsWithComponent(component)) {
			component.setPos(movingComponentInitialPos);
			component.setRotation(movingComponentInitialRotation);
		}
		board.refreshStatus();
		if (observer != null)
			observer.notify(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
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
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (oldPoint != null) {
			Point globalPos = component.getGlobalPos();
			Point diff = new Point((globalPos.x) - (oldPoint.x - e.getX()), (globalPos.y) - (oldPoint.y - e.getY()));
			component.setGlobalPos(diff);
		}
		oldPoint = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void finishController(boolean forced) {
		if (forced) {
			component.setPos(movingComponentInitialPos);
			component.setRotation(movingComponentInitialRotation);
		}
	}

	@Override
	public void startController() {
	}

}
