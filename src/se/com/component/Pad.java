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
	
	private static final long serialVersionUID = -5858148861127567375L;
	private List<Track> attachedTracks = new LinkedList<>();

	public Pad(Point pos) {
		this(pos, null);
	}
	
	public Pad(Point pos, GraphicObject parent) {
		super(pos, new Rectangle(0, 0, 9, 9), parent);
	}

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
		g.fillOval(0, 0, 10, 10);
	}

	public void detachTrack(Track track) {
		attachedTracks.remove(track);
	}
	
}
