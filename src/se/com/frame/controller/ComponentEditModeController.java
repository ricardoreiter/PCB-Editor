package se.com.frame.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

import se.com.component.BoardComponent;
import se.com.component.ComponentConfig;
import se.com.component.ComponentConfigFactory;
import se.com.frame.EditComponentsPanel;
import se.com.frame.MainFrame;
import se.com.frame.controller.internal.AddComponentInternalController;
import se.com.frame.controller.internal.BoardEditorInternalController;
import se.com.frame.controller.internal.BoardEditorInternalControllerObserver;
import se.com.frame.controller.internal.MovingComponentInternalController;
import se.com.frame.controller.internal.SelectingComponentInternalController;
import se.com.frame.model.ComponentListModel;
import se.com.frame.model.SelectedComponentTableModel;

/**
 * Controller of the component edit mode, when the user is adding/modifying components to the board.
 * Has 3 sub-controllers, see {@link AddComponentInternalController}, {@link MovingComponentInternalController} and {@link SelectingComponentInternalController}
 */
public class ComponentEditModeController extends MainFrameController implements FocusListener, BoardEditorInternalControllerObserver {

	private BoardComponent selectedComponent;
	private JList<ComponentConfig> componentList;
	private SelectedComponentTableModel selectedComponentTableModel = new SelectedComponentTableModel();
	
	public ComponentEditModeController(MainFrame mainFrame) {
		super(mainFrame);
		setNewInternalController(new SelectingComponentInternalController(mainFrame, this));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (internalController instanceof SelectingComponentInternalController && selectedComponent != null) {
			setNewInternalController(new MovingComponentInternalController(selectedComponent, this, mainFrame.getBoard()));
		}
		super.mouseDragged(e);
	}

	@Override
	public JPanel getControllerPanel() {
		EditComponentsPanel panel = new EditComponentsPanel();
		
		componentList = panel.getList();
		componentList.addFocusListener(this);
		
		List<ComponentConfig> components = new LinkedList<>();
		components.addAll(ComponentConfigFactory.getInstance().getComponents().values());
		
		ComponentListModel componentListModel = new ComponentListModel(components);
		componentList.setModel(componentListModel);
		
		panel.getSelectedComponentTable().setModel(selectedComponentTableModel);
		
		return panel;
	}

	@Override
	public void finishController() {
		mainFrame.getRenderPanel().clearTemporaryDrawables();
		super.finishController();
	}

	@Override
	public void notify(BoardEditorInternalController source) {
		if (source instanceof MovingComponentInternalController) {
			setNewInternalController(new SelectingComponentInternalController(mainFrame, this, selectedComponent));
		} else if (source instanceof SelectingComponentInternalController) {
			selectedComponent = ((SelectingComponentInternalController) source).getSelectedComponent();
			selectedComponentTableModel.setComponent(selectedComponent);
		} else if (source instanceof AddComponentInternalController) {
			setNewInternalController(new SelectingComponentInternalController(mainFrame, this, ((AddComponentInternalController) internalController).getLastAddedComponent()));
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// When the component list obtains focus, we change the sub-controller to AddComponentInternalController
		if (componentList.getSelectedValue() != null) {
			setNewInternalController(new AddComponentInternalController(componentList.getSelectedValue(), this, mainFrame));
			mainFrame.getRenderPanel().requestFocus();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
	
}
