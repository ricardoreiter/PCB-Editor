package se.com.frame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import se.com.component.ComponentConfig;

@SuppressWarnings("serial")
public class AddComponentsPanel extends JPanel {

	JList<ComponentConfig> list = new JList<ComponentConfig>();
	
	/**
	 * Create the panel.
	 */
	public AddComponentsPanel() {
		setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel label = new JLabel("Components");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label);
		
		add(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setAlignmentY(1.0f);

	}

	public JList<ComponentConfig> getList() {
		return list;
	}
	
}
