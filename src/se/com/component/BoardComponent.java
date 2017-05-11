package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import se.com.frame.render.Drawable;

public class BoardComponent implements Drawable {

	private final ComponentConfig componentConfig;
	private Point pos = new Point(0, 0);
	private int rotation = 0;
	
	public BoardComponent(ComponentConfig config) {
		this.componentConfig = config;
	}
	
	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
	
	public Point getPos() {
		return pos;
	}
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public void paint(Graphics2D g) {
		AffineTransform oldTransf = g.getTransform();
		
		g.translate(pos.getX(), pos.getY());
//		g.rotate(Math.toRadians(rotation), componentConfig.getBounds().getWidth() / 2, componentConfig.getBounds().getHeight() / 2);
		g.rotate(Math.toRadians(rotation));
		componentConfig.paint(g);
		
		g.setTransform(oldTransf);
	}

}
