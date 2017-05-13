package se.com.frame;

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

	public AddComponentsController(MainFrame mainFrame) {
		super(mainFrame);
	}

	private JList<ComponentConfig> componentList;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mainFrame.getBoard().addComponent(new BoardComponent(componentList.getSelectedValue(), e.getPoint()));
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
		mainFrame.getRenderPanel().setAddingComponentPos(e.getPoint());
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
		return panel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		mainFrame.getRenderPanel().setAddingComponent(componentList.getSelectedValue());
	}

	@Override
	public void finishController() {
		mainFrame.getRenderPanel().setAddingComponent(null);
	}

}
