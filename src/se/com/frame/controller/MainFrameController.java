package se.com.frame.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import se.com.frame.MainFrame;
import se.com.frame.controller.internal.BoardEditorInternalController;
import se.com.frame.controller.internal.BoardEditorInternalControllerObserver;

public abstract class MainFrameController implements MouseInputListener, KeyListener, BoardEditorInternalControllerObserver {

	protected MainFrame mainFrame;
	protected BoardEditorInternalController internalController;
	
	public MainFrameController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		internalController.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		internalController.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		internalController.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		internalController.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		internalController.mouseExited(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		internalController.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		internalController.mouseMoved(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		internalController.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		internalController.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		internalController.keyReleased(e);
	}
	
	protected void setNewInternalController(BoardEditorInternalController controller) {
		if (internalController != null) {
			internalController.finishController(false);
		}
		internalController = controller;
		internalController.startController();
	}
	
	public abstract JPanel getControllerPanel();
	
	public void finishController() {
		internalController.finishController(true);
	}

}
