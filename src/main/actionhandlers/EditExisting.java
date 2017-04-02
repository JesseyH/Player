package main.actionhandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;

import main.FolderBrowser;
import main.core.EditorViewController;
import main.core.FolderBrowserListener;


public class EditExisting implements ActionListener, FolderBrowserListener {

	private EditorViewController view;
	
	/**
	 * Constructor for this ActionListener in which the EditorView instance
	 * must be passed. The EditorView instance is of type EditorViewController
	 * so that the editor view can be controlled through this class.
	 * @param view The EditorView instance.
	 */
	public EditExisting(EditorViewController view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		FolderBrowser folderBrowser = new FolderBrowser((JFrame) view, this, true);
	}

	@Override
	public void onSuccess(File file) {
		System.out.println("CB: " + file);
		view.switchToEditorScreen();
	}

	@Override
	public void onFail() {
				
	}

}