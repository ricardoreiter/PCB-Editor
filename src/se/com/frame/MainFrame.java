package se.com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.com.component.Board;
import se.com.component.BoardLoader;
import se.com.frame.controller.ComponentEditModeController;
import se.com.frame.controller.MainFrameController;
import se.com.frame.controller.TrackEditModeController;
import se.com.frame.render.PCBRenderPanel;

public class MainFrame implements MouseInputListener, KeyListener {

	private JFrame frmPcbEditor;
	private JPanel rightPanel;
	private PCBRenderPanel renderPanel;
	private Board board = new Board(new Point(200, 200), new Rectangle(0, 0, 300, 300), 15, 1);
	private File openFile = null;
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
		
		renderPanel = new PCBRenderPanel(board);
		renderPanel.setBackground(Color.BLACK);
		frmPcbEditor.getContentPane().add(renderPanel, BorderLayout.CENTER);
		renderPanel.setLayout(new FlowLayout());
		
		JMenuBar menuBar = new JMenuBar();
		frmPcbEditor.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewBoardConfigurationDialog newBoardConfigurationDialog = new NewBoardConfigurationDialog();
				int result = newBoardConfigurationDialog.doModal();
				if (result == NewBoardConfigurationDialog.ID_OK) {
					openFile = null;
					setBoard(newBoardConfigurationDialog.getBoard());
					renderPanel.setBoard(board);
				}
			}
		});
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open file...");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFileDialog();
			}
		});
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(false);
			}
		});
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(true);
			}
		});
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmEditBoard = new JMenuItem("Edit Components");
		mntmEditBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setController(ComponentEditModeController.class);
			}
		});
		mnEdit.add(mntmEditBoard);
		
		JMenuItem mntmEditTracks = new JMenuItem("Edit Tracks");
		mntmEditTracks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setController(TrackEditModeController.class);
			}
		});
		mnEdit.add(mntmEditTracks);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mnEdit.add(mntmOptions);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmItemShoppingList = new JMenuItem("Item Shopping List");
		mntmItemShoppingList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShoppingListDialog shoppingListDialog = new ShoppingListDialog(board);
				shoppingListDialog.doModal();
			}
		});
		mnView.add(mntmItemShoppingList);
		setController(ComponentEditModeController.class);
		renderPanel.addMouseMotionListener(this);
		renderPanel.addMouseListener(this);
		renderPanel.addKeyListener(this);
	}
	
	private void setController(Class<? extends MainFrameController> controllerClass) {
		if (controller == null || controller.getClass() != controllerClass) {
			if (controller != null) {
				rightPanel.removeAll();
				renderPanel.removeMouseListener(controller);
				renderPanel.removeMouseMotionListener(controller);
				renderPanel.removeKeyListener(controller);
				controller.finishController();
			}
			try {
				controller = controllerClass.getConstructor(MainFrame.class).newInstance(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			renderPanel.addMouseListener(controller);
			renderPanel.addMouseMotionListener(controller);
			renderPanel.addKeyListener(controller);
			rightPanel.add(controller.getControllerPanel());
			frmPcbEditor.revalidate();
		}
	}

	public PCBRenderPanel getRenderPanel() {
		return renderPanel;
	}

	private void setBoard(Board board) {
		this.board = board;
		setController(ComponentEditModeController.class);
	}
	
	public Board getBoard() {
		return board;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		renderPanel.repaint();
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
		renderPanel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		renderPanel.repaint();
		renderPanel.requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		renderPanel.repaint();
	}
	
	private void openFileDialog() {
		JFileChooser c = new JFileChooser();
		c.setFileFilter(new FileNameExtensionFilter("*.pcb", "pcb"));
		int rVal = c.showOpenDialog(frmPcbEditor);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			openFile = c.getSelectedFile();
			try {
				setBoard(BoardLoader.loadBoard(c.getSelectedFile()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			renderPanel.setBoard(board);
		}
	}
	
	private void saveFile(boolean asNew) {
		if (asNew || (!asNew && openFile == null)) {
			JFileChooser c = new JFileChooser();
			c.setFileFilter(new FileNameExtensionFilter("*.pcb", "pcb"));
			int rVal = c.showSaveDialog(frmPcbEditor);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				openFile = c.getSelectedFile();
				try {
					BoardLoader.saveBoard(board, openFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				BoardLoader.saveBoard(board, openFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
