package main.actionhandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import main.FolderBrowser;
import main.FolderBrowserListener;

public class CreateNew implements ActionListener, FolderBrowserListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		FolderBrowser folderBrowser = new FolderBrowser(this, false);
		/*if(currentFile==null) {
			String fileName = JOptionPane.showInputDialog(Editor, "Name of scenario file:");
			if(fileName!=null && fileName.length()>0) {
				createScenario(fileName);
				JOptionPane.showMessageDialog(null,"Scenario file \""+currentFile+"\" is created");
			}
		} else {
			JOptionPane.showMessageDialog(null,"Scenario file alredy exists - "+currentFile);
		}*/
	}

	@Override
	public void onSuccess(File file) {
		System.out.println("CB: " + file);
	}

	@Override
	public void onFail() {
		// TODO Auto-generated method stub
		
	}

}