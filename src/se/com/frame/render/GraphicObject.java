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
 * A graphical object that can be attached children to it, and has a transformation matrix
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
		this.bounds = bounds;
		this.drawOrder = drawOrder;
		setParent(parent);
		setPos(pos);
	}
	
	/**
	 * New position, in local position (Relative to its parent, if no parent, then it's the global position)
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y) {
		setPos(new Point(x, y));
	}
	
	/**
	 * New position, in local position (Relative to its parent, if no parent, then it's the global position)
	 * @param x
	 * @param y
	 */
	public void setPos(Point pos) {
		this.pos = pos;
		updateTransform();
	}
	
	public int getRotation() {
		return rotation;
	}

	/**
	 * New rotation, relative to its parent
	 * @param rotation
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
		updateTransform();
	}
	
	/**
	 * @return the position of the object relative to its parent, the pivot is the middle of its bounds.
	 */
	public Point getPos() {
		return pos;
	}
	
	/**
	 * @return local pos minus height/2 and width/2
	 */
	private Point getMiddleCalculedPos() {
		return new Point((int) (pos.getX() - bounds.getCenterX()), (int) (pos.getY() - bounds.getCenterY()));
	}
	
	/**
	 * Set a new position in the global pos
	 * @param pos
	 */
	public void setGlobalPos(Point pos) {
		if (getParent() != null) {
			setPos(getParent().globalPosToLocalPos(pos));
		} else {
			setPos(pos);
		}
	}
	
	/**
	 * Transform a point from local pos (Relative to this object) to the global pos
	 * @param pos local pos
	 * @return transformed pos (Global Pos)
	 */
	public Point localPosToGlobalPos(Point pos) {
		Point result = new Point();
		transform.transform(pos, result);
		return result;
	}
	
	/**
	 * Transform a point from global pos to the local pos (Relative to this object)
	 * @param pos global pos
	 * @return transformed pos (Local Pos)
	 */
	public Point globalPosToLocalPos(Point pos) {
		Point result = new Point();
		try {
			transform.inverseTransform(pos, result);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return the global position of this object
	 */
	public Point getGlobalPos() {
		if (getParent() != null) {
			return getParent().localPosToGlobalPos(getPos());
		}
		return getPos();
	}

	/**
	 * @return object's parent, null if no parent
	 */
	public GraphicObject getParent() {
		return parent;
	}

	private void updateTransform() {
		AffineTransform newTransform = null;
		if (getParent() != null) {
			newTransform = getParent().getTransform();
		} else {
			newTransform = new AffineTransform();
		}
		Point pos = getMiddleCalculedPos();
		newTransform.rotate(Math.toRadians(rotation), this.pos.getX(), this.pos.getY());
		newTransform.translate(pos.getX(), pos.getY());
		transform = newTransform;
		for (GraphicObject o : children) {
			o.updateTransform();
		}
	}
	
	/**
	 * Paint this component and all the other components
	 */
	@Override
	public void paint(Graphics2D g) {
		AffineTransform oldTransf = g.getTransform();
		
		g.setTransform(transform);
		internalPaint(g);
		
		for (GraphicObject child : children) {
			child.paint(g);
		}
		
		g.setTransform(oldTransf);
	}
	
	/**
	 * Add a new child to this object, sorted by the drawOrder
	 * To add a new child, see {@link #setParent(GraphicObject)}
	 * @param obj to be added
	 */
	private void addChild(GraphicObject obj) {
		int pos = Collections.binarySearch(children, obj);
		if (pos < 0) {
			pos = -pos - 1;
		}
		children.add(pos, obj);
	}
	
	/**
	 * Remove a child
	 * To remove a child, see {@link #setParent(GraphicObject)}
	 * @param obj to be removed
	 */
	private void removeChild(GraphicObject obj) {
		children.remove(obj);
	}
	
	/**
	 * Set the new parent to this object.
	 * This will add/remove the obj as the parent's child
	 * @param obj to be added, or null to remove
	 */
	public void setParent(GraphicObject obj) {
		if (this.parent != null) 
			this.parent.removeChild(this);
		
		if (obj != null)
			obj.addChild(this);
		
		this.parent = obj;
	}
	
	/**
	 * @return the bounds of this object, in local pos
	 */
	public Rectangle getBounds() {
		return (Rectangle) bounds.clone();
	}
	
	/**
	 * @return the bounds of this object, in global pos
	 */
	public Rectangle getGlobalBounds() {
		Rectangle ownBounds = transform.createTransformedShape(getBounds()).getBounds();
		for (GraphicObject child : children) {
			ownBounds.add(child.getGlobalBounds());
		} 
		ownBounds.grow(GlobalConfig.getInstance().getComponentsGlobalBoundsSizeBonus(), 
				GlobalConfig.getInstance().getComponentsGlobalBoundsSizeBonus());
		return ownBounds;
	}
	
	/**
	 * @return a copy of the object's transform
	 */
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
	
	/**
	 * The internal paint of the object
	 * @param g
	 */
	public abstract void internalPaint(Graphics2D g);

}
