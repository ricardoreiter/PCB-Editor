package se.com.frame.controller.internal;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.Pad;
import se.com.component.Track;
import se.com.frame.MainFrame;

public class EditTrackInternalController implements BoardEditorInternalController {

	private Pad highlightedPad;
	private Track track;
	private MainFrame mainFrame;
	private BoardEditorInternalControllerObserver observer;
	private Point currentPoint;
	private int angleStep = 15;

	public EditTrackInternalController(MainFrame mainFrame, BoardEditorInternalControllerObserver observer, Track track) {
		this.mainFrame = mainFrame;
		this.track = track;
		this.observer = observer;
		this.currentPoint = this.track.getLastPoint();
		this.track.setPadB(null);
	}
	
	public EditTrackInternalController(MainFrame mainFrame, BoardEditorInternalControllerObserver observer, Pad pad) {
		this(mainFrame, observer, new Track(pad));
		currentPoint = (Point) track.getLastPoint().clone();
		track.addPoint(currentPoint); 
		mainFrame.getBoard().addTrack(track);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (highlightedPad != null) {
				track.removeLastPoint();
				track.setPadB(highlightedPad);
				observer.notify(this);
			} else {
				currentPoint = new Point(e.getPoint());
				track.addPoint(currentPoint);
			}
		} else {
			track.removeLastPoint();
			currentPoint = track.getLastPoint();
			if (track.getPoints().size() == 1) {
				mainFrame.getBoard().removeTrack(track);
				observer.notify(this);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Pad selectedPad = mainFrame.getBoard().getPadAtPos(e.getPoint());
		if (selectedPad != null) {
			highlightedPad = selectedPad;
			currentPoint.setLocation(selectedPad.getGlobalPos());
		} else {
			highlightedPad = null;
			currentPoint.setLocation(adjustPos(track.getPoints().get(track.getPoints().size() - 2), e.getPoint()));
		}
	}
	
	private Point adjustPos(Point originPos, Point pos) {
		double difX = pos.x - originPos.x;
		double difY = pos.y - originPos.y;
		double length = originPos.distance(pos);
		
		double rotAng = Math.toDegrees(Math.atan2(difX,-difY));
		rotAng = (rotAng + 360) % 360;  
		
		long rotateAux = Math.round(rotAng / angleStep);
		double newAngle = rotateAux * angleStep - 90;
		
		int endX = originPos.x + (int)(Math.cos(Math.toRadians(newAngle)) * length);
		int endY = originPos.y + (int)(Math.sin(Math.toRadians(newAngle)) * length);
		
		return new Point(endX, endY);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			mainFrame.getBoard().removeTrack(track);
			observer.notify(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startController() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishController(boolean forced) {
		if (forced) {
			mainFrame.getBoard().removeTrack(track);
		}
	}

}
