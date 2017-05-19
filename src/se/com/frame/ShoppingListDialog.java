package se.com.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import se.com.component.Board;
import se.com.component.BoardComponent;

public class ShoppingListDialog extends JDialog {

	private static final long serialVersionUID = -8097785910430843981L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ShoppingListDialog(Board board) {
		setBounds(100, 100, 339, 499);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setText(getBoardShoppingList(board));
			JScrollPane scrollPane = new JScrollPane(textArea);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private String getBoardShoppingList(Board board) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("1x %d-layer Board", board.getLayers())).append("\n");
		HashMap<String, Integer> componentsQuantity = new HashMap<>();
		
		List<BoardComponent> components = board.getComponents();
		for (BoardComponent component : components) {
			Integer quantity = componentsQuantity.get(component.getConfig().getName());
			if (quantity == null) {
				quantity = 1;
			} else {
				quantity++;
			}
			componentsQuantity.put(component.getConfig().getName(), quantity);
		}
		
		for (Entry<String, Integer> entry : componentsQuantity.entrySet()) {
			sb.append(String.format("%dx %s", entry.getValue(), entry.getKey())).append("\n");
		}
		
		return sb.toString();
	}

	public void doModal() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

}
