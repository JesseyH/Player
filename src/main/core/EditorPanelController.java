package main.core;

/**
 * Interface that the EditorPanel class implements so that other objects
 * can control the EditorPanel.
 * @author Group 6
 *
 */
public interface EditorPanelController {

	/**
	 * Refresh the text area of the main editor panel that shows the overall
	 * scenario buffer text.
	 */
	public void refreshBuffer();
	
}
