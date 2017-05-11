package se.com.frame.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import se.com.component.Board;
import se.com.component.BoardComponent;
import se.com.component.ComponentConfigFactory;

@SuppressWarnings("serial")
public class PCBRenderPanel extends JPanel {

	private Grid grid = new Grid(50);
	private Board board = new Board();
	
	public PCBRenderPanel() {
		BoardComponent component = new BoardComponent(ComponentConfigFactory.getInstance().getComponent("resistor"));
		component.setPos(300, 300);
		component.setRotation(90);
		board.addComponent(component);
		component = new BoardComponent(ComponentConfigFactory.getInstance().getComponent("resistor"));
		component.setPos(200, 200);
		board.addComponent(component);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Dimension size = getSize();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size.width, size.height);
		Graphics2D g2 = (Graphics2D) g;
		grid.paint(g2);
		
		board.paint(g2);
	}
	

}
