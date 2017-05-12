package se.com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

import se.com.component.Board;
import se.com.component.BoardComponent;
import se.com.component.ComponentConfig;
import se.com.component.ComponentConfigFactory;
import se.com.frame.render.PCBRenderPanel;

public class MainFrame implements ListSelectionListener, MouseInputListener {

	private JFrame frmPcbEditor;
	private PCBRenderPanel renderPanel;
	private ComponentListModel componentListModel;
	private JList<ComponentConfig> componentList;
	private MainFrameState state = MainFrameState.NONE;
	private Board board = new Board();

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
		frmPcbEditor.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmPcbEditor.getContentPane().add(rightPanel, BorderLayout.EAST);
		List<ComponentConfig> components = new LinkedList<>();
		components.addAll(ComponentConfigFactory.getInstance().getComponents().values());
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		rightPanel.add(panel_1);
		
		JLabel lblComponents = new JLabel("Components");
		lblComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblComponents.setAlignmentY(Component.TOP_ALIGNMENT);
		
		componentList = new JList<>();
		componentList.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		componentList.setBorder(new LineBorder(new Color(0, 0, 0)));
		componentList.addListSelectionListener(this);
		
		componentListModel = new ComponentListModel(components);
		componentList.setModel(componentListModel);
		componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));
		panel_1.add(lblComponents);
		panel_1.add(componentList);
		
		JPanel topPanel = new JPanel();
		frmPcbEditor.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		renderPanel = new PCBRenderPanel(board);
		renderPanel.setBackground(Color.BLACK);
		frmPcbEditor.getContentPane().add(renderPanel, BorderLayout.CENTER);
		renderPanel.setLayout(new FlowLayout());
		renderPanel.addMouseListener(this);
		renderPanel.addMouseMotionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		state = MainFrameState.ADDING_NEW_COMPONENT;
		renderPanel.setAddingComponent(componentList.getSelectedValue());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
			case ADDING_NEW_COMPONENT:
				board.addComponent(new BoardComponent(componentList.getSelectedValue(), e.getPoint()));
				break;
			default:
				break;
		}
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
		switch (state) {
			case ADDING_NEW_COMPONENT:
				renderPanel.setAddingComponentPos(e.getPoint());
				break;
			default:
				break;
		}
		
		renderPanel.repaint();
	}

}
