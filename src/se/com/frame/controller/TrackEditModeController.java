package se.com.frame.controller;

import javax.swing.JPanel;

import se.com.frame.MainFrame;
import se.com.frame.controller.internal.AddTrackInternalController;
import se.com.frame.controller.internal.BoardEditorInternalController;
import se.com.frame.controller.internal.BoardEditorInternalControllerObserver;

public class TrackEditModeController extends MainFrameController implements BoardEditorInternalControllerObserver {

	public TrackEditModeController(MainFrame mainFrame) {
		super(mainFrame);
		setNewInternalController(new AddTrackInternalController(mainFrame));
	}

	@Override
	public JPanel getControllerPanel() {
		return new JPanel();
	}

	@Override
	public void notify(BoardEditorInternalController source) {
		
	}

}
