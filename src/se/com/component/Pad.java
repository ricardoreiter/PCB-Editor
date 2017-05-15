package se.com.component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import se.com.config.GlobalConfig;
import se.com.frame.render.GraphicObject;

public class Pad extends GraphicObject {

	public Pad(Point pos) {
		super(pos);
	}
	
	public Pad(Point pos, GraphicObject parent) {
		super(pos, parent);
		bounds = new Rectangle(0, 0, 9, 9);
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
		g.setColor(GlobalConfig.getInstance().getTrackColor());
		g.setStroke(new BasicStroke(GlobalConfig.getInstance().getPadWidth()));
		g.drawArc(0, 0, 10, 10, 0, 360);
	}
	
}