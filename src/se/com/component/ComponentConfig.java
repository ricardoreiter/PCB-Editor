package se.com.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import se.com.config.GlobalConfig;
import se.com.frame.render.Drawable;
import se.com.util.Line;

public class ComponentConfig implements Drawable, Serializable {

	private static final long serialVersionUID = -3360378534521468993L;
	public static final Color COMPONENT_COLOR = Color.YELLOW;
	public static final Color BOUNDS_COLOR = Color.WHITE;

	private String name;
	private Rectangle bounds;
	private Polygon shape;
	private List<Line> pads = new LinkedList<>();
	
	public ComponentConfig(String name, Rectangle bounds, Polygon shape, List<Line> pads) {
		this.name = name;
		this.bounds = bounds;
		this.shape = shape;
		this.pads.addAll(pads);
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setStroke(new BasicStroke(GlobalConfig.getInstance().getComponentShapeWidth()));
		if (GlobalConfig.getInstance().isShowComponentBounds()) {
			g.setColor(BOUNDS_COLOR);
			g.drawRect(0, 0, bounds.width, bounds.height);
		}
		
		g.setColor(COMPONENT_COLOR);
		g.drawPolygon(shape);
		for (Line pad : pads) {
			g.drawLine((int) pad.getStartX(), (int) pad.getStartY(), (int) pad.getEndX(), (int) pad.getEndY());
		}
	}

	public String getName() {
		return name;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public List<Point> getPadsLocations() {
		List<Point> result = new LinkedList<>();
		for (Line pad : pads) {
			result.add(new Point((int) pad.getEndX(), (int) pad.getEndY())); 
		}
		return result;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
