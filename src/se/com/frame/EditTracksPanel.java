package se.com.frame;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EditTracksPanel extends JPanel {

	private static final long serialVersionUID = -3841771071744166102L;
	private JComboBox<Integer> combo = new JComboBox<Integer>();
	/**
	 * Create the panel.
	 */
	public EditTracksPanel() {
		setLayout(new GridLayout(6, 1, 0, 0));
		
		JLabel lblLayer = new JLabel("Layer");
		lblLayer.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblLayer);
		
		combo.setEditable(false);
		combo.setFocusable(false);
		add(combo);
	}

	public JComboBox<Integer> getCombo() {
		return combo;
	}
	
	
	
}
