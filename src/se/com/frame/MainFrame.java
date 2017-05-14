package se.com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;

import se.com.component.Board;
import se.com.frame.render.PCBRenderPanel;

public class MainFrame implements MouseInputListener {

	private JFrame frmPcbEditor;
	private JPanel rightPanel;
	private PCBRenderPanel renderPanel;
	private Board board = new Board();
	private MainFrameController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
		
		rightPanel = new JPanel();
		frmPcbEditor.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmPcbEditor.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JToolBar toolBar = new JToolBar();
		topPanel.add(toolBar);
		
		renderPanel = new PCBRenderPanel(board);
		renderPanel.setBackground(Color.BLACK);
		frmPcbEditor.getContentPane().add(renderPanel, BorderLayout.CENTER);
		renderPanel.setLayout(new FlowLayout());
		
		JMenuBar menuBar = new JMenuBar();
		frmPcbEditor.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open file...");
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmAddNewComponents = new JMenuItem("Add New Components");
		mntmAddNewComponents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setController(AddComponentsController.class);
			}
		});
		mnEdit.add(mntmAddNewComponents);
		
		JMenuItem mntmAddNewTracks = new JMenuItem("Add New Tracks");
		mnEdit.add(mntmAddNewTracks);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmEditBoard = new JMenuItem("Edit Board");
		mntmEditBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setController(EditComponentsController.class);
			}
		});
		mnEdit.add(mntmEditBoard);
		
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mnEdit.add(mntmOptions);
		setController(EditComponentsController.class);
		renderPanel.addMouseMotionListener(this);
		renderPanel.addMouseListener(this);
	}
	
	private void setController(Class<? extends MainFrameController> controllerClass) {
		if (controller == null || controller.getClass() != controllerClass) {
			if (controller != null) {
				rightPanel.removeAll();
				renderPanel.removeMouseListener(controller);
				renderPanel.removeMouseMotionListener(controller);
				controller.finishController();
			}
			try {
				controller = controllerClass.getConstructor(MainFrame.class).newInstance(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			renderPanel.addMouseListener(controller);
			renderPanel.addMouseMotionListener(controller);
			rightPanel.add(controller.getControllerPanel());
			frmPcbEditor.revalidate();
		}
	}

	public PCBRenderPanel getRenderPanel() {
		return renderPanel;
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		renderPanel.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		renderPanel.repaint();
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
	
}
