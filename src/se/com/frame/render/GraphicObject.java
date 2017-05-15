package se.com.frame.render;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;

/**
 * A graphical object that can be attached children to it
 * @author Ricardo Reiter
 */
public abstract class GraphicObject implements Drawable {

	private GraphicObject parent;
	private AffineTransform transform = new AffineTransform();
	protected Point pos;
	protected int rotation;
	protected Rectangle bounds;
	private List<GraphicObject> children = new LinkedList<>();
	
	public GraphicObject(Point pos) {
		this(pos, null);
	}
	
	public GraphicObject(Point pos, GraphicObject parent) {
		this.parent = parent;
		this.pos = pos;
		if (this.parent != null) {
			parent.addChild(this);
		}
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
		transform.translate(pos.getX(), pos.getY());
		transform.rotate(Math.toRadians(rotation));
		Point result = new Point(0, 0);
		transform.transform(new Point(0, 0), result);
		return result;
	}

	public Point getGlobalPos() {
		if (getParent() != null) {
			Point result = new Point(0, 0);
			transform.transform(new Point(0, 0), result);
			return result;
		}
		return getPos();
	}

	public Drawable getParent() {
		return parent;
	}

	@Override
	public void paint(Graphics2D g) {
		AffineTransform oldTransf = g.getTransform();
		
		g.translate(pos.getX(), pos.getY());
		g.rotate(Math.toRadians(rotation));
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
	
	public void removeChild(GraphicObject obj) {
		children.remove(obj);
	}
	
	public Rectangle getBounds() {
		return transform.createTransformedShape(bounds).getBounds();
	}
	
	public AffineTransform getTransform() {
		return (AffineTransform) transform.clone();
	}
	
	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}
	
	public abstract void internalPaint(Graphics2D g);

}
