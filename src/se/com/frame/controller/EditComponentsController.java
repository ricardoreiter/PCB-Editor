package se.com.frame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import se.com.component.BoardComponent;
import se.com.frame.EditComponentsPanel;
import se.com.frame.MainFrame;

public class EditComponentsController extends MainFrameController implements BoardEditorInternalControllerObserver {

	private BoardComponent selectedComponent;
	private BoardEditorInternalController internalController;
	
	public EditComponentsController(MainFrame mainFrame) {
		super(mainFrame);
		setNewInternalController(new SelectingComponentInternalController(mainFrame, this));
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
	public JPanel getControllerPanel() {
		EditComponentsPanel panel = new EditComponentsPanel();
		
		final BoardEditorInternalControllerObserver observer = this;
		panel.getBtnMoveComponent().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedComponent != null) {
					setNewInternalController(new MovingComponentInternalController(selectedComponent, observer));
				}
			}
		});
		
		return panel;
	}

	@Override
	public void finishController() {
		internalController.finishController(true);
	}

	@Override
	public void controllerFinished(BoardEditorInternalController source) {
		if (source instanceof MovingComponentInternalController) {
			setNewInternalController(new SelectingComponentInternalController(mainFrame, this));
		} else if (source instanceof SelectingComponentInternalController) {
			selectedComponent = ((SelectingComponentInternalController) source).getSelectedComponent();
		}
	}
	
	private void setNewInternalController(BoardEditorInternalController controller) {
		if (internalController != null) {
			internalController.finishController(false);
		}
		internalController = controller;
	}

}
