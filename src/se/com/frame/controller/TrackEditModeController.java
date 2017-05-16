package se.com.frame.controller;

import javax.swing.JPanel;

import se.com.frame.MainFrame;
import se.com.frame.controller.internal.BoardEditorInternalController;
import se.com.frame.controller.internal.BoardEditorInternalControllerObserver;
import se.com.frame.controller.internal.EditTrackInternalController;
import se.com.frame.controller.internal.SelectTrackInternalController;

public class TrackEditModeController extends MainFrameController implements BoardEditorInternalControllerObserver {

	public TrackEditModeController(MainFrame mainFrame) {
		super(mainFrame);
		setNewInternalController(new SelectTrackInternalController(mainFrame, this));
	}

	@Override
	public JPanel getControllerPanel() {
		return new JPanel();
	}

	@Override
	public void notify(BoardEditorInternalController source) {
		if (source instanceof SelectTrackInternalController) {
			SelectTrackInternalController selectTrackInternalController = (SelectTrackInternalController) source;
			if (selectTrackInternalController.getTrack() != null) {
				setNewInternalController(new EditTrackInternalController(mainFrame, this, selectTrackInternalController.getTrack()));
			} else {
				setNewInternalController(new EditTrackInternalController(mainFrame, this, selectTrackInternalController.getPad()));
			}
		} else if (source instanceof EditTrackInternalController) {
			setNewInternalController(new SelectTrackInternalController(mainFrame, this));
		}
	}

}
