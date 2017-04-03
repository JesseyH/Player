package main.core;

/**
 * This interface defines methods which can be called by other classes in order to 
 * control features on the EditorView. The EditorView class must implement this interface.
 * @author Group 6
 *
 */
public interface EditorViewController {

	/**
	 * Sets the "selected directory" text on the editor view.
	 * @param directory The directory that the scenario file will be saved to. 
	 */
	public void setDirectoryText(String directory);
	
	/**
	 * Makes the EditorView switch to the MainEditor JPanel in which
	 * all scenario editing buttons are visible.
	 */
	public void switchToEditorScreen();
	
	/**
	 * Returns the scenario file name entered into the editor screen.
	 * @return The scenario file name.
	 */
	public String getScenarioFileName();
	
	/**
	 * Returns the directory in which the scenario file should be saved into.
	 * @return The directory to save the scenario file into.
	 */
	public String getScenarioFileDir();
	
	public String getBrailleCells();
	
	public String getButtons();
	
	/**
	 * Called when an popup containing an error message should be displayed.
	 * @param errorMessage The error message to display
	 */
	public void showErrorMessage(String errorMessage);
	
}
