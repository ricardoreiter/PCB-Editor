package se.com.frame.controller.internal;

import java.awt.event.KeyListener;

import javax.swing.event.MouseInputListener;

/**
 * A Sub-controller of the MainFrameController, can be represented as the "state" of the current controller 
 */
public interface BoardEditorInternalController extends MouseInputListener, KeyListener {

	void startController();
	
	void finishController(boolean forced);
	
}
