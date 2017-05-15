package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import se.com.config.GlobalConfig;
import se.com.frame.render.Drawable;

public class Track implements Drawable {

	private Pad padA;
	private Pad padB;
	private List<Point> points;
	
	public Track() {
		points = new ArrayList<>();
	}
	
	public Pad getPadA() {
		return padA;
	}

	public void setPadA(Pad padA) {
		this.padA = padA;
		addPoint(padA.getGlobalPos());
	}

	public Pad getPadB() {
		return padB;
	}

	public void setPadB(Pad padB) {
		this.padB = padB;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(GlobalConfig.getInstance().getTrackColor());
		Point firstPoint = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			Point secondPoint = points.get(i);
			g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
			firstPoint = secondPoint;
		}
	}

	public void addPoint(Point point) {
		points.add(point);
	}

}
