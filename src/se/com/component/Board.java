package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import se.com.config.GlobalConfig;
import se.com.frame.render.GraphicObject;

public class Board extends GraphicObject {

	private static final long serialVersionUID = -7519434690422307644L;
	private List<BoardComponent> components = new LinkedList<>();
	private List<Track> tracks = new LinkedList<>();
	private int workableAreaConstraint;
	private int layers;

	public Board(Point pos, Rectangle boardSize, int workableAreaConstraint, int layers) {
		super(pos, boardSize);
		this.workableAreaConstraint = workableAreaConstraint;
		this.layers = layers;
	}

	public void addComponent(BoardComponent component) {
		components.add(component);
	}
	
	public BoardComponent getComponentAtPos(Point point) {
		List<BoardComponent> filtered = components.stream().filter(component -> {
											return component.getGlobalBounds().contains(point);
										}).collect(Collectors.toList());
		Optional<BoardComponent> componentAtPos = filtered.stream().min((comp1, comp2) -> {
			Rectangle bounds1 = comp1.getGlobalBounds();
			Rectangle bounds2 = comp2.getGlobalBounds();
			double distanceComp1 = Point.distance(bounds1.getCenterX(), bounds1.getCenterY(), point.getX(), point.getY());
			double distanceComp2 = Point.distance(bounds2.getCenterX(), bounds2.getCenterY(), point.getX(), point.getY());
			if (distanceComp1 == distanceComp2) return 0;
			if (distanceComp1 > distanceComp2) return 1;
			return -1;
		});
		return componentAtPos.orElse(null);
	}

	public void addTrack(Track track) {
		tracks.add(track);
	}

	public Pad getPadAtPos(Point point) {
		List<Pad> pads = new LinkedList<>();
		components.forEach(comp -> pads.addAll(comp.getPads()));
		List<Pad> filtered = pads.stream().filter(pad -> {return pad.getGlobalBounds().contains(point);}).collect(Collectors.toList());
		if (filtered.size() > 0) {
			return filtered.get(0);
		}
		return null;
	}

	public void removeTrack(Track track) {
		tracks.remove(track);
		if (track.getPadA() != null) {
			track.getPadA().detachTrack(track);
		}
		if (track.getPadB() != null) {
			track.getPadB().detachTrack(track);
		}
		track.setParent(null);
	}

	public Rectangle getWorkableArea() {
		Rectangle result = (Rectangle) getBounds().clone();
		result.grow(-workableAreaConstraint, -workableAreaConstraint);
		return result;
	}

	@Override
	public void internalPaint(Graphics2D g) {
		g.setColor(GlobalConfig.getInstance().getBoardColor());
		g.drawRect(0, 0, bounds.width, bounds.height);
		
		g.setColor(GlobalConfig.getInstance().getWorkableAreaColor());
		Rectangle workableArea = getWorkableArea();
		g.drawRect(workableArea.x, workableArea.y, workableArea.width, workableArea.height);
	}

	public List<BoardComponent> getComponents() {
		return components;
	}

	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

}
