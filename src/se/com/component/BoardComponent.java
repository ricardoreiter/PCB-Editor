package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import se.com.frame.render.GraphicObject;

public class BoardComponent extends GraphicObject {

	private final ComponentConfig componentConfig;
	private List<Pad> pads = new LinkedList<>();
	
	public BoardComponent(ComponentConfig config) {
		this(config, new Point(0, 0));
	}
	
	public BoardComponent(ComponentConfig config, Point point) {
		super(point);
		assert config == null : "Violation: config is null";
		this.componentConfig = config;
		this.bounds = config.getBounds();
		createPads();
	}

	public BoardComponent(BoardComponent addingComponent) {
		this(addingComponent.getConfig(),(Point) addingComponent.getPos().clone());
		this.rotation = addingComponent.getRotation();
		setTransform(addingComponent.getTransform());
	}

	public ComponentConfig getConfig() {
		return componentConfig;
	}

	public List<Pad> getPads() {
		return pads;
	}

	private void createPads() {
		List<Point> padsLocations = componentConfig.getPadsLocations();
		for (Point p : padsLocations) {
			pads.add(new Pad(p, this));
		}
	}

	@Override
	public void internalPaint(Graphics2D g) {
		componentConfig.paint(g);
	}
	
}
