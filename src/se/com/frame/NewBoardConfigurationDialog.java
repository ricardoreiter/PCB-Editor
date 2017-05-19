package se.com.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import se.com.component.Board;

public class NewBoardConfigurationDialog extends JDialog {

	public static final int ID_OK = 1;
    public static final int ID_CANCEL = 0;
	private static final long serialVersionUID = 1L;
	
	private int exitCode = ID_CANCEL;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtBoardWidth;
	private JTextField txtBoardHeigth;
	private JTextField txtBoardComponentSpacing;
	private JLabel lblBoardWidth;
	private JLabel lblBoardheigth;
	private JLabel lblBoardComponentSpacing;
	private Board board;
	private JLabel lblLayers;
	private JTextField txtLayers;

	public int doModal() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
		return exitCode;
	}

	/**
	 * Create the dialog.
	 */
	public NewBoardConfigurationDialog() {
		setTitle("New Board");
		setBounds(100, 100, 288, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblBoardWidth = new JLabel("Board Width");
			lblBoardWidth.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			txtBoardWidth = new JTextField();
			txtBoardWidth.setText("400");
			txtBoardWidth.setColumns(10);
		}
		{
			txtBoardHeigth = new JTextField();
			txtBoardHeigth.setText("200");
			txtBoardHeigth.setColumns(10);
		}
		{
			txtBoardComponentSpacing = new JTextField();
			txtBoardComponentSpacing.setText("10");
			txtBoardComponentSpacing.setColumns(10);
		}
		contentPanel.setLayout(new GridLayout(0, 2, 5, 2));
		contentPanel.add(lblBoardWidth);
		contentPanel.add(txtBoardWidth);
		{
			lblBoardheigth = new JLabel("Board Heigth");
			lblBoardheigth.setHorizontalAlignment(SwingConstants.CENTER);
		}
		contentPanel.add(lblBoardheigth);
		contentPanel.add(txtBoardHeigth);
		{
			lblBoardComponentSpacing = new JLabel("Board Component Spacing");
			lblBoardComponentSpacing.setHorizontalAlignment(SwingConstants.CENTER);
		}
		contentPanel.add(lblBoardComponentSpacing);
		contentPanel.add(txtBoardComponentSpacing);
		{
			lblLayers = new JLabel("Layers");
			lblLayers.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblLayers);
		}
		{
			txtLayers = new JTextField();
			txtLayers.setText("1");
			contentPanel.add(txtLayers);
			txtLayers.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JDialog frame = this;
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int height = Integer.valueOf(txtBoardHeigth.getText());
							int width = Integer.valueOf(txtBoardWidth.getText());
							int workableArea = Integer.valueOf(txtBoardComponentSpacing.getText());
							int layers = Integer.valueOf(txtLayers.getText());
							if (layers > 4) {
								JOptionPane.showMessageDialog(frame, "Maximum layers number is 4!", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
							exitCode = ID_OK;
							board = new Board(new Point((width / 2) + 50, (height / 2) + 50), new Rectangle(0, 0, width, height), workableArea, layers);
							setVisible(false);
						} catch (NumberFormatException exception) {
							JOptionPane.showMessageDialog(frame, "Some values are invalid!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						exitCode = ID_CANCEL;
						board = null;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Board getBoard() {
		return board;
	}

}
