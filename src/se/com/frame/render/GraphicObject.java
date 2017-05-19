package se.com.frame.render;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import se.com.config.GlobalConfig;

/**
 * A graphical object that can be attached children to it
 * @author Ricardo Reiter
 */
public abstract class GraphicObject implements Drawable, Serializable, Comparable<GraphicObject> {

	private static final long serialVersionUID = -2594334930572741377L;
	private GraphicObject parent;
	private AffineTransform transform = new AffineTransform();
	protected Point pos;
	protected int rotation;
	protected Rectangle bounds;
	protected int drawOrder;
	private ArrayList<GraphicObject> children = new ArrayList<>();
	
	public GraphicObject(Point pos, Rectangle bounds, int drawOrder) {
		this(pos, bounds, drawOrder, null);
	}
	
	public GraphicObject(Point pos, Rectangle bounds, int drawOrder, GraphicObject parent) {
		this.pos = pos;
		this.bounds = bounds;
		this.drawOrder = drawOrder;
		setParent(parent);
	}
	
	public void setPos(int x, int y) {
		setPos(new Point(x, y));
	}
	
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public Point getPos() {
		return pos;
	}
	
	private Point getMiddleCalculedPos() {
		return new Point((int) (pos.getX() - bounds.getCenterX()), (int) (pos.getY() - bounds.getCenterY()));
	}
	
	public void setGlobalPos(Point pos) {
		if (getParent() != null) {
			setPos(getParent().globalPosToLocalPos(pos));
		} else {
			setPos(pos);
		}
	}
	
	public Point localPosToGlobalPos(Point pos) {
		Point result = new Point();
		transform.transform(pos, result);
		return result;
	}
	
	public Point globalPosToLocalPos(Point pos) {
		Point result = new Point();
		try {
			transform.inverseTransform(pos, result);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Point getGlobalPos() {
		if (getParent() != null) {
			return getParent().localPosToGlobalPos(getPos());
		}
		return getPos();
	}

	public GraphicObject getParent() {
		return parent;
	}

	@Override
	public void paint(Graphics2D g) {
		AffineTransform oldTransf = g.getTransform();
		
		Point pos = getMiddleCalculedPos();
		g.rotate(Math.toRadians(rotation), this.pos.getX(), this.pos.getY());
		g.translate(pos.getX(), pos.getY());
		transform = g.getTransform();
		internalPaint(g);
		
		for (GraphicObject child : children) {
			child.paint(g);
		}
		
		g.setTransform(oldTransf);
	}
	
	private void addChild(GraphicObject obj) {
		int pos = Collections.binarySearch(children, obj);
		if (pos < 0) {
			pos = -pos - 1;
		}
		children.add(pos, obj);
	}
	
	private void removeChild(GraphicObject obj) {
		children.remove(obj);
	}
	
	public void setParent(GraphicObject obj) {
		if (this.parent != null) 
			this.parent.removeChild(this);
		
		if (obj != null)
			obj.addChild(this);
		
		this.parent = obj;
	}
	
	public Rectangle getBounds() {
		return (Rectangle) bounds.clone();
	}
	
	public Rectangle getGlobalBounds() {
		Rectangle ownBounds = transform.createTransformedShape(getBounds()).getBounds();
		for (GraphicObject child : children) {
			ownBounds.add(child.getGlobalBounds());
		} 
		ownBounds.grow(GlobalConfig.getInstance().getComponentsGlobalBoundsSizeBonus(), 
				GlobalConfig.getInstance().getComponentsGlobalBoundsSizeBonus());
		return ownBounds;
	}
	
	public AffineTransform getTransform() {
		return (AffineTransform) transform.clone();
	}
	
	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}
	
	 public int compareTo(GraphicObject o) {
		 if (this.drawOrder < o.drawOrder) {
			 return -1;
		 } else if (this.drawOrder == o.drawOrder) {
			 return 0;
		 }
		 return 1;
	 }
	
	public abstract void internalPaint(Graphics2D g);

}
