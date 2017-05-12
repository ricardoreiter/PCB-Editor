package se.com.frame.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import se.com.component.Board;
import se.com.component.BoardComponent;
import se.com.component.ComponentConfig;
import se.com.component.ComponentConfigFactory;

@SuppressWarnings("serial")
public class PCBRenderPanel extends JPanel {

	private Grid grid = new Grid(50);
	private Board board;
	private BoardComponent addingComponent = null;

	public PCBRenderPanel(Board board) {
		super();
		BoardComponent component = new BoardComponent(ComponentConfigFactory.getInstance().getComponent("resistor"));
		component.setPos(300, 300);
		component.setRotation(90);
		board.addComponent(component);
		component = new BoardComponent(ComponentConfigFactory.getInstance().getComponent("resistor"));
		component.setPos(200, 200);
		board.addComponent(component);
		this.board = board;
	}
	
	public void setAddingComponent(ComponentConfig componentConfig) {
		if (componentConfig == null) {
			addingComponent = null;
		} else if (addingComponent == null || addingComponent.getConfig() != componentConfig) {
			this.addingComponent = new BoardComponent(componentConfig);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
		Dimension size = getSize();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size.width, size.height);
		Graphics2D g2 = (Graphics2D) g;
		grid.paint(g2);
		
		board.paint(g2);
		
		if (addingComponent != null) {
			addingComponent.paint(g2);
		}
	}

	public void setAddingComponentPos(Point point) {
		if (this.addingComponent != null) {
			addingComponent.setPos(point.x, point.y);
		}
	}
	

}
