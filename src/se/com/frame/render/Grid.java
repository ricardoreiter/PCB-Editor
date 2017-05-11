package se.com.frame.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Grid implements Drawable {

	private int spacing;
	private Color color = Color.GRAY;
	
	public Grid(int spacing) {
		setSpacing(spacing);
	}
	
	@Override
	public void paint(Graphics2D g) {
		Rectangle dimensions = g.getClipBounds();
		g.setColor(color);
		
		int currentColumn = spacing;
		while (currentColumn <= dimensions.width) {
			g.drawLine(currentColumn, 0, currentColumn, dimensions.height);
			currentColumn += spacing;
		}
		
		int currentRow = spacing;
		while (currentRow <= dimensions.height) {
			g.drawLine(0, currentRow, dimensions.width, currentRow);
			currentRow += spacing;
		}
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
