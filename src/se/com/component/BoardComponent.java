package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import se.com.frame.render.GraphicObject;

public class BoardComponent extends GraphicObject {

	private static final long serialVersionUID = 6862662924117934209L;
	private final ComponentConfig componentConfig;
	private List<Pad> pads = new LinkedList<>();
	
	public BoardComponent(ComponentConfig config, Board board) {
		this(config, new Point(0, 0), board);
	}
	
	public BoardComponent(ComponentConfig config, Point point, Board board) {
		super(point, config.getBounds(), board);
		assert config == null : "Violation: config is null";
		this.componentConfig = config;
		createPads();
	}

	public BoardComponent(BoardComponent addingComponent, Board board) {
		this(addingComponent.getConfig(),(Point) addingComponent.getPos().clone(), board);
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

	public void rotate() {
		setRotation(getRotation() + 90);
	}
	
}
