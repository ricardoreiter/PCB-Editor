package se.com.frame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import se.com.component.ComponentConfig;

@SuppressWarnings("serial")
public class EditComponentsPanel extends JPanel {

	private JList<ComponentConfig> list = new JList<ComponentConfig>();
	private JTable table;
	
	/**
	 * Create the panel.
	 */
	public EditComponentsPanel() {
		setLayout(new GridLayout(6, 1, 0, 0));
		
		JLabel label = new JLabel("Add New Component");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label);
		
		add(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setAlignmentY(1.0f);
		list.setFocusable(false);
		
		JLabel lblSelected = new JLabel("Selected Component");
		lblSelected.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSelected);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setFocusable(false);
		add(table);
	}

	public JList<ComponentConfig> getList() {
		return list;
	}
	
	public JTable getSelectedComponentTable() {
		return table;
	}
	
}
