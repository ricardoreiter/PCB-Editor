package se.com.frame.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import se.com.config.GlobalConfig;

public class HighlightBox implements Drawable {

	private Color color;
	private GraphicObject obj;
	
	public HighlightBox(GraphicObject obj, Color color) {
		super();
		this.color = color;
		this.obj = obj;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setStroke(new BasicStroke(GlobalConfig.getInstance().getHighlightBoxWidth()));
		g.setColor(color);
		AffineTransform oldT = g.getTransform();
		
		Rectangle rect = obj.getGlobalBounds();
		g.translate(rect.getX(), rect.getY());
		g.drawRect(0, 0, rect.width, rect.height);
		
		g.setTransform(oldT);
	}

}
