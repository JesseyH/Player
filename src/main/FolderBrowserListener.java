package main;

import java.io.File;

/**
 * Any class that would like to be notified of of a FolderBrowser event 
 * should implement this class.
 * @author Group 6
 *
 */
public interface FolderBrowserListener {

	/**
	 * Called when the user has successfully selected a file/folder through
	 * the FolderBrowser.
	 * @param file The file/folder that was selected.
	 */
	void onSuccess(File file);
	
	/**
	 * Called when the user does not end up selecting a file/folder through
	 * the folder browser.
	 */
	void onFail();
	
}
