package se.com.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import se.com.frame.render.GraphicObject;

public class Pad extends GraphicObject {

	public Pad(Point pos) {
		super(pos);
	}
	
	public Pad(Point pos, GraphicObject parent) {
		super(pos, parent);
	}

	private List<Track> attachedTracks = new LinkedList<>();

	public void attachTrack(Track track) {
		attachedTracks.add(track);
	}
	
	public List<Track> getAttachedTracks() {
		return attachedTracks;
	}
	
	@Override
	public void internalPaint(Graphics2D g) {
		g.fillOval(-3, -3, 7, 7);
	}
	
}
