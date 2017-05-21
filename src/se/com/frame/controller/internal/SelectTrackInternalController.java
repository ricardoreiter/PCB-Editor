package se.com.frame.controller.internal;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.Pad;
import se.com.component.Track;
import se.com.frame.MainFrame;
import se.com.frame.render.HighlightBox;
import se.com.util.ColorUtils;

public class SelectTrackInternalController implements BoardEditorInternalController {

	private Pad pad;
	private HighlightBox padHighlightedBox;
	private Track track;
	private MainFrame mainFrame;
	private BoardEditorInternalControllerObserver observer;

	public SelectTrackInternalController(MainFrame mainFrame, BoardEditorInternalControllerObserver observer) {
		this.mainFrame = mainFrame;
		this.observer = observer;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (pad != null) {
			observer.notify(this);
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
			if (pad != selectedPad) {
				mainFrame.getRenderPanel().removeTemporaryDrawable(padHighlightedBox);
				
				pad = selectedPad;
				padHighlightedBox = new HighlightBox(pad, ColorUtils.LIGHT_GREEN);
				mainFrame.getRenderPanel().addTemporaryDrawable(padHighlightedBox);
			}
		} else {
			mainFrame.getRenderPanel().removeTemporaryDrawable(padHighlightedBox);
			pad = null;
			padHighlightedBox = null;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void startController() {
	}

	@Override
	public void finishController(boolean forced) {
		mainFrame.getRenderPanel().clearTemporaryDrawables();
		pad = null;
		padHighlightedBox = null;
	}

	public Track getTrack() {
		return track;
	}
	
	public Pad getPad() {
		return pad;
	}

}
