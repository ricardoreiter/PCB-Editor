package se.com.component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import se.com.config.GlobalConfig;
import se.com.frame.render.GraphicObject;
import se.com.util.Line;

public class Pad extends GraphicObject {
	
	private static final long serialVersionUID = -5858148861127567375L;
	private List<Track> attachedTracks = new LinkedList<>();
	private int radius = 5;

	public Pad(Point pos) {
		this(pos, null);
	}
	
	public Pad(Point pos, GraphicObject parent) {
		super(pos, new Rectangle(0, 0, 9, 9), 50, parent);
	}

	public void attachTrack(Track track) {
		attachedTracks.add(track);
	}
	
	public List<Track> getAttachedTracks() {
		return attachedTracks;
	}
	
	@Override
	public void internalPaint(Graphics2D g) {
		g.setColor(GlobalConfig.getInstance().getTrackColor(0));
		g.setStroke(new BasicStroke(GlobalConfig.getInstance().getPadWidth()));
		g.fillOval(0, 0, radius * 2, radius * 2);
	}

	public void detachTrack(Track track) {
		attachedTracks.remove(track);
	}
	
	public boolean collide(Line line) {
		return getGlobalBounds().intersectsLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	}
	
}
