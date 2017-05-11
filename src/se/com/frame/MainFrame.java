package se.com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import se.com.frame.render.PCBRenderPanel;

public class MainFrame {

	private JFrame frmPcbEditor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					MainFrame window = new MainFrame();
					window.frmPcbEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPcbEditor = new JFrame();
		frmPcbEditor.setTitle("PCB Editor");
		frmPcbEditor.setBounds(100, 100, 761, 523);
		frmPcbEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel rightPanel = new JPanel();
		frmPcbEditor.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JList componentList = new JList();
		componentList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Test", "test 2"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rightPanel.add(componentList);
		
		JPanel topPanel = new JPanel();
		frmPcbEditor.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		PCBRenderPanel pcbPanel = new PCBRenderPanel();
		pcbPanel.setBackground(Color.WHITE);
		frmPcbEditor.getContentPane().add(pcbPanel, BorderLayout.CENTER);
		pcbPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		pcbPanel.add(panel);
	}

}
