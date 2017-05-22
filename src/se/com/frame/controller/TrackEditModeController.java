package se.com.frame.controller;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import se.com.frame.EditTracksPanel;
import se.com.frame.MainFrame;
import se.com.frame.controller.internal.BoardEditorInternalController;
import se.com.frame.controller.internal.BoardEditorInternalControllerObserver;
import se.com.frame.controller.internal.EditTrackInternalController;
import se.com.frame.controller.internal.SelectTrackInternalController;
import se.com.frame.model.LayerListModel;

/**
 * Controller of the track edition mode, when the user is adding/changing new tracks to the board
 * Has 2 sub-controllers, see {@link SelectTrackInternalController} and {@link EditTrackInternalController}
 */
public class TrackEditModeController extends MainFrameController implements BoardEditorInternalControllerObserver {

	private JComboBox<Integer> layerList;
	
	public TrackEditModeController(MainFrame mainFrame) {
		super(mainFrame);
		setNewInternalController(new SelectTrackInternalController(mainFrame, this));
	}

	@Override
	public JPanel getControllerPanel() {
		EditTracksPanel panel = new EditTracksPanel();
		layerList = panel.getCombo();
		layerList.setModel(new LayerListModel(mainFrame.getBoard()));
		layerList.setSelectedIndex(0);
		return panel;
	}

	@Override
	public void notify(BoardEditorInternalController source) {
		if (source instanceof SelectTrackInternalController) {
			SelectTrackInternalController selectTrackInternalController = (SelectTrackInternalController) source;
			if (selectTrackInternalController.getTrack() != null) {
				setNewInternalController(new EditTrackInternalController(mainFrame, this, selectTrackInternalController.getTrack()));
			} else {
				setNewInternalController(new EditTrackInternalController(mainFrame, this, selectTrackInternalController.getPad(), (Integer) layerList.getSelectedItem()));
			}
		} else if (source instanceof EditTrackInternalController) {
			setNewInternalController(new SelectTrackInternalController(mainFrame, this));
		}
	}

}
