package se.com.component;

/**
 * Listens to changes in a Board's status
 */
public interface BoardStatusListener {

	/**
	 * The Board's status changed
	 * @param errors current errors, if any
	 */
	void statusChanged(BoardErrors[] errors);
	
}
