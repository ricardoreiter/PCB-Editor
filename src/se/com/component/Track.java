package se.com.component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.geom.Line2D;

import se.com.config.GlobalConfig;
import se.com.frame.render.GraphicObject;
import se.com.util.Line;

public class Track extends GraphicObject {

	private static final long serialVersionUID = -2339474505450151917L;
	private Pad padA;
	private Pad padB;
	private List<Point> points;
	private int layer;
	
	public Track(Pad pad, GraphicObject parent, int layer) {
		super(new Point(), new Rectangle(), 40-layer, parent);
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

	public int getLayer() {
		return layer;
	}

	/**
	 * Get the lines of the track, in Global Pos
	 * @return lines
	 */
	public List<Line> getLinesGlobalPos() {
		List<Line> result = new LinkedList<>();
		
		Point firstPoint = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			Point secondPoint = points.get(i);
			result.add(new Line(localPosToGlobalPos(firstPoint), localPosToGlobalPos(secondPoint)));
			firstPoint = secondPoint;
		}
		
		return result;
	}
	
	/**
	 * Check if the line collides with this track
	 * @param line
	 * @return
	 */
	public boolean collides(Line line) {
		Point startLocalPos = globalPosToLocalPos(line.getStart());
		Point endLocalPos = globalPosToLocalPos(line.getEnd());
		
		int startIndex = 0;
		Point firstPoint = points.get(startIndex++);
		// If the line is starting/ending in the same point, we have to ignore the intersect, 
		// because they are starting/ending at the same position
		if (firstPoint.equals(startLocalPos) || firstPoint.equals(endLocalPos)) {
			firstPoint = points.get(startIndex++);
		}
		
		int lastIndex = points.size() - 1;
		Point lastPoint = points.get(lastIndex);
		// Same as above
		if (lastPoint.equals(startLocalPos) || lastPoint.equals(endLocalPos)) {
			lastIndex--;
		}
		
		for (; startIndex <= lastIndex; startIndex++) {
			Point secondPoint = points.get(startIndex);
			if (Line2D.linesIntersect(startLocalPos.x, startLocalPos.y, endLocalPos.x, endLocalPos.y, firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y)) {
				return true;
			}
			firstPoint = secondPoint;
		}
		return false;
	}

}
