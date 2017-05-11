package se.com.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.RectBounds;

import se.com.config.GlobalConfig;
import se.com.frame.render.Drawable;

public class ComponentConfig implements Drawable {

	public static final Color COMPONENT_COLOR = Color.YELLOW;
	public static final Color BOUNDS_COLOR = Color.WHITE;

	private String name;
	private Rectangle bounds;
	private Polygon shape;
	private List<Line2D> pads = new LinkedList<>();
	
	public ComponentConfig(String name, Rectangle bounds, Polygon shape, List<Line2D> pads) {
		this.name = name;
		this.bounds = bounds;
		this.shape = shape;
		this.pads.addAll(pads);
	}
	
	@Override
	public void paint(Graphics2D g) {
		if (GlobalConfig.getInstance().isShowComponentBounds()) {
			g.setColor(BOUNDS_COLOR);
			g.drawRect(0, 0, bounds.width, bounds.height);
		}
		
		g.setColor(COMPONENT_COLOR);
		g.drawPolygon(shape);
		for (Line2D pad : pads) {
			RectBounds bounds = pad.getBounds();
			g.drawLine((int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(), (int) bounds.getMaxY());
		}
	}

	public String getName() {
		return name;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
}
