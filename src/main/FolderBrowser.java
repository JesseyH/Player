package main;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.core.FolderBrowserListener;

/**
 * Opens a Java FolderBrowser upon instantiation.
 * @author Group 6
 *
 */
public class FolderBrowser {

	/**
	 * Constructor for the FolderBrowser.
	 * @param parentFrame The parent JFrame in which the JFileChooser will be attached to.
	 * @param listener The FolderBrowserListener
	 * @param load If this boolean is set to true, then we are loading a scenario file
	 * and thus we should allow only txt files to be selected. If this boolean is set to false
	 * then we are creating a scenario file and thus must only allow directories to be selected and no files.
	 */
	public FolderBrowser(JFrame parentFrame, FolderBrowserListener listener, boolean load) {
		
		//Instantiate the JavaFileChooser with title and initial directory.
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(load ? "Open Your Scenario File (Must Be txt file)" : "Choose a Directory to Save Scenario File Into");
		
		//Disable ability to select all file types.
		chooser.setAcceptAllFileFilterUsed(false);
		
		//If we're creating a new scenario file, allow only directories to be selected.
		if(!load) {
			
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
		//If we're loading a scenario file, allow on txt files to be selected.
		} else if(load) {
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			chooser.setFileFilter(filter);
			
		}
		
		// Open the file chooser and if they have pressed okay, called the listener's
		// on success method and pass the file/folder that was selected.
		if (chooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
			listener.onSuccess(chooser.getCurrentDirectory());
		} else { // If file/folder selection failed, call the listeners on fail method.
			listener.onFail();
		}
		
	}
	
	
}
