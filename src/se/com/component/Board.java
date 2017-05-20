package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import se.com.config.GlobalConfig;
import se.com.frame.render.GraphicObject;
import se.com.util.Line;

public class Board extends GraphicObject {

	private static final long serialVersionUID = -7519434690422307644L;
	private List<BoardComponent> components = new LinkedList<>();
	private List<Track> tracks = new LinkedList<>();
	private int workableAreaConstraint;
	private int layers;

	public Board(Point pos, Rectangle boardSize, int workableAreaConstraint, int layers) {
		super(pos, boardSize, 0);
		this.workableAreaConstraint = workableAreaConstraint;
		this.layers = layers;
	}

	public void addComponent(BoardComponent component) {
		components.add(component);
	}
	
	public void removeComponent(BoardComponent component) {
		List<Pad> pads = component.getPads();
		for (Pad p : pads) {
			List<Track> tracks = new LinkedList<>();
			tracks.addAll(p.getAttachedTracks());
			for (Track t : tracks) {
				removeTrack(t);
			}
		}
		components.remove(component);
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
	
	/**
	 * Check if the line is free to create a track. Should not have any tracks/pads on the way.
	 * @param line in Global Pos
	 * @param layer
	 * @param ignoreList
	 * @return path is free
	 */
	public boolean isLineFree(Line line, int layer, GraphicObject... ignoreList) {
		return getElementsAlongLine(line, layer, ignoreList).size() == 0;
	}

	public List<GraphicObject> getElementsAlongLine(Line line, int layer, GraphicObject... ignoreList) {
		HashSet<GraphicObject> ignoreSet = new HashSet<>(Arrays.asList(ignoreList));
		List<GraphicObject> result = new LinkedList<>();
		for (Track t : tracks) {
			if (!ignoreSet.contains(t)) {
				if (t.getLayer() == layer) {
					if (t.collide(line)) {
						result.add(t);
					}
				}
			}
		}
		for (BoardComponent c : components) {
			if (!ignoreSet.contains(c)) {
				if (c.collide(line, ignoreSet)) {
					result.add(c);
				}
			}
		}
		return result;
	}

	/**
	 * Check if line is inside the workable area
	 * @param line in Global Pos
	 * @return
	 */
	public boolean isInsideWorkableArea(Line line) {
		Rectangle rect = getWorkableArea();
		return rect.contains(globalPosToLocalPos(line.getStart())) && rect.contains(globalPosToLocalPos(line.getEnd()));
	}
	
	/**
	 * Check if rect is inside the workable area
	 * @param rect
	 * @return
	 */
	public boolean isInsideWorkableArea(Rectangle rect) {
		Rectangle workableArea = getWorkableArea();
		rect.setLocation(globalPosToLocalPos(rect.getLocation()));
		return workableArea.contains(rect);
	}
		
}
