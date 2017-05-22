package se.com.frame.controller.internal;

/**
 * Watches for changes in the sub-controllers
 */
public interface BoardEditorInternalControllerObserver {

	/**
	 * A change occurred in the sub-controller
	 */
	void notify(BoardEditorInternalController source);
	
}
