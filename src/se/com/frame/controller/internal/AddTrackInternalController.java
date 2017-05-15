package se.com.frame.controller.internal;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import se.com.component.Pad;
import se.com.component.Track;
import se.com.frame.MainFrame;
import se.com.frame.render.HighlightBox;
import se.com.util.ColorUtils;

public class AddTrackInternalController implements BoardEditorInternalController {

	private Pad padHighlighted;
	private HighlightBox padHighlightedBox;
	private Track track;
	private MainFrame mainFrame;

	public AddTrackInternalController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
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
		Pad pad = mainFrame.getBoard().getPadAtPos(e.getPoint());
		if (pad != null) {
			if (padHighlighted != pad) {
				mainFrame.getRenderPanel().removeTemporaryDrawable(padHighlightedBox);
				
				padHighlighted = pad;
				padHighlightedBox = new HighlightBox(padHighlighted, ColorUtils.LIGHT_GREEN);
				mainFrame.getRenderPanel().addTemporaryDrawable(padHighlightedBox);
			}
		} else {
			mainFrame.getRenderPanel().removeTemporaryDrawable(padHighlightedBox);
			padHighlighted = null;
			padHighlightedBox = null;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
