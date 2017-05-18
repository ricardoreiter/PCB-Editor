package se.com.frame.render;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import se.com.config.GlobalConfig;

/**
 * A graphical object that can be attached children to it
 * @author Ricardo Reiter
 */
public abstract class GraphicObject implements Drawable, Serializable {

	private static final long serialVersionUID = -2594334930572741377L;
	private GraphicObject parent;
	private AffineTransform transform = new AffineTransform();
	protected Point pos;
	protected int rotation;
	protected Rectangle bounds;
	private List<GraphicObject> children = new LinkedList<>();
	
	public GraphicObject(Point pos, Rectangle bounds) {
		this(pos, bounds, null);
	}
	
	public GraphicObject(Point pos, Rectangle bounds, GraphicObject parent) {
		this.pos = pos;
		this.bounds = bounds;
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
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(rotation), pos.getX(), pos.getY());
		transform.translate(pos.getX(), pos.getY());
		Point result = new Point(0, 0);
		transform.transform(new Point(0, 0), result);
		return result;
	}
	
	private Point getMiddleCalculedPos() {
		return new Point((int) (pos.getX() - bounds.getCenterX()), (int) (pos.getY() - bounds.getCenterY()));
	}
	
	public void setGlobalPos(Point pos) {
		if (getParent() != null) {
			Point parentPos = getParent().getMiddleCalculedPos();
			setPos(new Point(pos.x - parentPos.x, pos.y - parentPos.y));
		} else {
			setPos(pos);
		}
	}

	public Point getGlobalPos() {
		if (getParent() != null) {
			Point result = new Point(0, 0);
			transform.transform(new Point((int) bounds.getCenterX(), (int) bounds.getCenterY()), result);
			return result;
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
		children.add(obj);
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
	
	public abstract void internalPaint(Graphics2D g);

}
