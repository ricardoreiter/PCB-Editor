package se.com.frame.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import se.com.component.Board;

@SuppressWarnings("serial")
public class PCBRenderPanel extends JPanel {

	private Grid grid = new Grid(50);
	private Board board;
	private List<Drawable> temporaryDrawables = new LinkedList<Drawable>();

	public PCBRenderPanel(Board board) {
		super();
		setFocusable(true);
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
			}
		});
		thread.start();
		
		this.board = board;
	}
	
	public void setBoard(Board board) {
		clearTemporaryDrawables();
		this.board = board;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension size = getSize();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size.width, size.height);
		grid.paint(g2);
		
		board.paint(g2);
		
		for (Drawable drawable : temporaryDrawables) {
			drawable.paint(g2);
		}
		board.refreshStatus();
	}

	public void addTemporaryDrawable(Drawable drawable) {
		temporaryDrawables.add(drawable);
	}
	
	public void removeTemporaryDrawable(Drawable drawable) {
		temporaryDrawables.remove(drawable);
	}
	
	public void clearTemporaryDrawables() {
		temporaryDrawables.clear();
	}

}
