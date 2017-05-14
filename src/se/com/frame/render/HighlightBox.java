package se.com.frame.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class HighlightBox implements Drawable {

	private Color color;
	private Rectangle rect;
	
	public HighlightBox(Rectangle rect, Color color) {
		super();
		this.color = color;
		this.rect = (Rectangle) rect.clone();
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(color);
		AffineTransform oldT = g.getTransform();
		
		g.translate(rect.getX(), rect.getY());
		g.drawRect(0, 0, rect.width, rect.height);
		
		g.setTransform(oldT);
	}

}
