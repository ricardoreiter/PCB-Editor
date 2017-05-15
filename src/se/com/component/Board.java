package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import se.com.frame.render.Drawable;

public class Board implements Drawable {

	private List<BoardComponent> components = new LinkedList<>();
	private List<Track> tracks = new LinkedList<>();

	public void addComponent(BoardComponent component) {
		components.add(component);
	}
	
	@Override
	public void paint(Graphics2D g) {
		for (BoardComponent comp : components) {
			comp.paint(g);
		}
		for (Track track : tracks) {
			track.paint(g);
		}
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
	
}
