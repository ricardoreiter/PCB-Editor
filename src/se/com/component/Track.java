package se.com.component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import se.com.config.GlobalConfig;
import se.com.frame.render.GraphicObject;

public class Track extends GraphicObject {

	private static final long serialVersionUID = -2339474505450151917L;
	private Pad padA;
	private Pad padB;
	private List<Point> points;
	private int layer;
	
	public Track(Pad pad, GraphicObject parent, int layer) {
		super(new Point(), new Rectangle(), parent);
		points = new ArrayList<>();
		this.layer = layer;
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
			addPointWorldPos(padA.getGlobalPos());
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
			addPointWorldPos(padB.getGlobalPos());
		}
	}

	public void updatePadsLocation() {
		if (getPadA() != null) {
			setPointWorldPos(0, getPadA().getGlobalPos());
		}
		if (getPadB() != null) {
			setPointWorldPos(points.size()-1, getPadB().getGlobalPos());
		}
	}

	public void addPointWorldPos(Point point) {
		points.add(globalPosToLocalPos(point));
	}
	
	public void setPointWorldPos(int pos, Point point) {
		points.set(pos, globalPosToLocalPos(point));
	}
	
	/**
	 * Get the points of this Track, in Local Position (Relative to its parent)
	 * @return
	 */
	public List<Point> getPoints() {
		return points;
	}
	
	public void removeLastPoint() {
		points.remove(points.size() - 1);
	}

	@Override
	public void internalPaint(Graphics2D g) {
		g.setColor(GlobalConfig.getInstance().getTrackColor(layer));
		g.setStroke(new BasicStroke(GlobalConfig.getInstance().getTrackWidth()));
		
		updatePadsLocation();
		
		Point firstPoint = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			Point secondPoint = points.get(i);
			g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
			firstPoint = secondPoint;
		}
	}

	public Point getLastPointWorldPos() {
		return localPosToGlobalPos(points.get(points.size() - 1));
	}

	public void setLastPointWorldPos(Point point) {
		points.set(points.size() - 1, globalPosToLocalPos(point));
	}

}
