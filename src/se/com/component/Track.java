package se.com.component;

import java.awt.BasicStroke;
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
	
	public Track(Pad pad) {
		points = new ArrayList<>();
		setPadA(pad);
	}
	
	public Pad getPadA() {
		return padA;
	}

	public void setPadA(Pad padA) {
		if(this.padA != null) {
			this.padA.detachTrack(this);
		}
		
		this.padA = padA;
		if (padA != null) {
			padA.attachTrack(this);
			addPoint(padA.getGlobalPos());
		}
		
	}

	public Pad getPadB() {
		return padB;
	}

	public void setPadB(Pad padB) {
		if(this.padB != null) {
			this.padB.detachTrack(this);
		}
		
		this.padB = padB;
		if (padB != null) {
			padB.attachTrack(this);
			addPoint(padB.getGlobalPos());
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(GlobalConfig.getInstance().getTrackColor());
		g.setStroke(new BasicStroke(GlobalConfig.getInstance().getTrackWidth()));
		
		updatePadsLocation();
		
		Point firstPoint = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			Point secondPoint = points.get(i);
			g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
			firstPoint = secondPoint;
		}
	}

	public void updatePadsLocation() {
		if (getPadA() != null) {
			points.set(0, getPadA().getGlobalPos());
		}
		if (getPadB() != null) {
			points.set(points.size()-1, getPadB().getGlobalPos());
		}
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public List<Point> getPoints() {
		return points;
	}
	
	public Point getLastPoint() {
		return points.get(points.size() - 1);
	}

	public void removeLastPoint() {
		points.remove(points.size() - 1);
	}

}
