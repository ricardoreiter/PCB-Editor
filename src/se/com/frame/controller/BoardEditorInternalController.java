package se.com.frame.controller;

import java.awt.event.KeyListener;

import javax.swing.event.MouseInputListener;

public interface BoardEditorInternalController extends MouseInputListener, KeyListener {

	void finishController(boolean forced);
	
}
