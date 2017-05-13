package se.com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import se.com.component.BoardComponent;
import se.com.component.ComponentConfig;
import se.com.component.ComponentConfigFactory;

public class AddComponentsController extends MainFrameController implements ListSelectionListener {

	private BoardComponent addingComponent = null;
	
	public AddComponentsController(MainFrame mainFrame) {
		super(mainFrame);
	}

	private JList<ComponentConfig> componentList;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (addingComponent != null)
			mainFrame.getBoard().addComponent(new BoardComponent(addingComponent));
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
		if (addingComponent != null)
			addingComponent.setPos(e.getX(), e.getY());
	}

	@Override
	public JPanel getControllerPanel() {
		AddComponentsPanel panel = new AddComponentsPanel();
		
		componentList = panel.getList();
		componentList.addListSelectionListener(this);
		
		List<ComponentConfig> components = new LinkedList<>();
		components.addAll(ComponentConfigFactory.getInstance().getComponents().values());
		
		ComponentListModel componentListModel = new ComponentListModel(components);
		componentList.setModel(componentListModel);
		
		panel.getRotateButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (addingComponent != null) {
					addingComponent.setRotation(addingComponent.getRotation() + 90);
				}
			}
		});
		return panel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		addingComponent = new BoardComponent(componentList.getSelectedValue());
		mainFrame.getRenderPanel().clearTemporaryDrawables();
		mainFrame.getRenderPanel().addTemporaryDrawable(addingComponent);
	}

	@Override
	public void finishController() {
		mainFrame.getRenderPanel().clearTemporaryDrawables();
	}

}
