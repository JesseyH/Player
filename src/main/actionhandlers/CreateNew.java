package main.actionhandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;

import main.FolderBrowser;
import main.core.EditorViewController;
import main.core.FolderBrowserListener;

public class CreateNew implements ActionListener, FolderBrowserListener {
	
	private EditorViewController view;	
	
	/**
	 * Constructor for this ActionListener in which the EditorView instance
	 * must be passed. The EditorView instance is of type EditorViewController
	 * so that the editor view can be controlled through this class.
	 * @param view The EditorView instance.
	 */
	public CreateNew(EditorViewController view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		new FolderBrowser((JFrame) view, this, false);
	}

	@Override
	public void onSuccess(File file) {
		view.setDirectoryText(file.toString());
	}

	@Override
	public void onFail() {
		// TODO Auto-generated method stub
		
	}

}