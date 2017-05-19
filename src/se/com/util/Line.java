package se.com.util;

import java.awt.Point;
import java.io.Serializable;

public class Line implements Serializable {
	
	private static final long serialVersionUID = -5218021876220846020L;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	public Line(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public Line(Point startLocalPos, Point endLocalPos) {
		this(startLocalPos.x, startLocalPos.y, endLocalPos.x, endLocalPos.y);
	}

	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getEndX() {
		return endX;
	}
	public void setEndX(int endX) {
		this.endX = endX;
	}
	public int getEndY() {
		return endY;
	}
	public void setEndY(int endY) {
		this.endY = endY;
	}

	public Point getEnd() {
		return new Point(endX, endY);
	}

	public Point getStart() {
		return new Point(startX, startY);
	}

}
