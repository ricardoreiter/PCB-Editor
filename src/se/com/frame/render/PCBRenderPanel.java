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
import se.com.component.BoardComponent;
import se.com.component.ComponentConfigFactory;

@SuppressWarnings("serial")
public class PCBRenderPanel extends JPanel {

	private Grid grid = new Grid(50);
	private Board board;
	private List<Drawable> temporaryDrawables = new LinkedList<Drawable>();

	public PCBRenderPanel(Board board) {
		super();
		setFocusable(true);
		BoardComponent component = new BoardComponent(ComponentConfigFactory.getInstance().getComponent("resistor"));
		component.setPos(300, 200);
		board.addComponent(component);
		BoardComponent component2 = new BoardComponent(ComponentConfigFactory.getInstance().getComponent("resistor"));
		component2.setPos(300, 300);
		board.addComponent(component2);
		
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
