package main.actionhandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.core.EditorViewController;
import main.core.Scenario;

public class StartEditing implements ActionListener {

	private EditorViewController view;
	
	/**
	 * Constructor for this ActionListener in which the EditorView instance
	 * must be passed. The EditorView instance is of type EditorViewController
	 * so that the editor view can be controlled through this class.
	 * @param view The EditorView instance.
	 */
	public StartEditing(EditorViewController view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(view.getScenarioFileName().length() == 0) {
			view.showErrorMessage("Please enter the name of the new scenario file!");
		} else if (view.getScenarioFileDir().equals("No Directory Selected...") || view.getScenarioFileDir().equals("")) {
			view.showErrorMessage("Please select a directory to save the scenario file to!");
		} else {
			try {
				int brailleCells = Integer.parseInt(view.getBrailleCells());
				int buttons = Integer.parseInt(view.getButtons());
				Scenario.initialize(view.getScenarioFileName(), view.getScenarioFileDir(), brailleCells, buttons);
				view.switchToEditorScreen();
			} catch (NumberFormatException e) {
				view.showErrorMessage("Only enter numbers for the number of braille cells and buttons!");
			}
			
		}
	}

}
