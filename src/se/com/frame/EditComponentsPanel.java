package se.com.frame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class EditComponentsPanel extends JPanel {

	private JTable table;
	private JButton btnMoveComponent;

	/**
	 * Create the panel.
	 */
	public EditComponentsPanel() {
		setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel lblSelected = new JLabel("Selected");
		lblSelected.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSelected);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"Property", "Value"
			}
		));
		add(table);
		
		btnMoveComponent = new JButton("Move Component");
		add(btnMoveComponent);

	}
	
	public JButton getBtnMoveComponent() {
		return btnMoveComponent;
	}
	
}
