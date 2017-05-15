package se.com.frame.controller.internal;

import java.awt.event.KeyListener;

import javax.swing.event.MouseInputListener;

public interface BoardEditorInternalController extends MouseInputListener, KeyListener {

	void startController();
	
	void finishController(boolean forced);
	
}
