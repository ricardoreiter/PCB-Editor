package se.com.frame.controller.internal;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.Board;
import se.com.component.Pad;
import se.com.component.Track;
import se.com.frame.MainFrame;
import se.com.frame.controller.TrackEditModeController;
import se.com.util.Line;

/**
 * Sub-controller of {@link TrackEditModeController}. Controls a Track on the board, adding/removing new points. 
 */
public class EditTrackInternalController implements BoardEditorInternalController {

	private Pad highlightedPad;
	private Track track;
	private MainFrame mainFrame;
	private BoardEditorInternalControllerObserver observer;
	private int angleStep = 15;

	public EditTrackInternalController(MainFrame mainFrame, BoardEditorInternalControllerObserver observer, Track track) {
		this.mainFrame = mainFrame;
		this.track = track;
		this.observer = observer;
		this.track.setPadB(null);
	}
	
	public EditTrackInternalController(MainFrame mainFrame, BoardEditorInternalControllerObserver observer, Pad pad, int layer) {
		this(mainFrame, observer, new Track(pad, mainFrame.getBoard(), layer));
		track.addPointWorldPos(track.getLastPointWorldPos()); 
		mainFrame.getBoard().addTrack(track);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Board board = mainFrame.getBoard();
		if (e.getButton() == MouseEvent.BUTTON1) {
			Point lastLastPoint = track.localPosToGlobalPos(track.getPoints().get(track.getPoints().size() - 2));
			Line line = new Line(lastLastPoint.x, lastLastPoint.y, track.getLastPointWorldPos().x, track.getLastPointWorldPos().y);
			if (board.isLineFree(line, track.getLayer(), track, highlightedPad, track.getPadA()) && board.isInsideWorkableArea(line)) {
				if (highlightedPad != null) {
					track.removeLastPoint();
					track.setPadB(highlightedPad);
					observer.notify(this);
				} else {
					track.addPointWorldPos(track.getLastPointWorldPos());
				}
			}
		} else {
			track.removeLastPoint();
			if (track.getPoints().size() == 1) {
				track.setParent(null);
				board.removeTrack(track);
				observer.notify(this);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Pad selectedPad = mainFrame.getBoard().getPadAtPos(e.getPoint());
		if (selectedPad != null) {
			highlightedPad = selectedPad;
			track.setLastPointWorldPos(selectedPad.getGlobalPos());
		} else {
			highlightedPad = null;
			track.setLastPointWorldPos(adjustPos(track.localPosToGlobalPos(track.getPoints().get(track.getPoints().size() - 2)), e.getPoint()));
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
	}

	@Override
	public void startController() {
	}

	@Override
	public void finishController(boolean forced) {
		if (forced) {
			mainFrame.getBoard().removeTrack(track);
		}
	}

}
