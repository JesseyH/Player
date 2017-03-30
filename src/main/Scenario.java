package main;

import java.io.File;

public class Scenario {
	
	//Static variable to hold the singleton instance of the scenario file currently being worked on.
	private static Scenario scenarioFile;
	
	/**
	 * Getter method for the scenario singleton instance.
	 * @return The scenario instance being used.
	 */
	public static Scenario get() {
		if(scenarioFile == null) {
			try {
				throw new Exception("Scenario file was not loaded or created!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return scenarioFile;
	}

	/**
	 * Loads an existing scenario file or creates a new scenario file.
	 * @param load True if we're loading an existing scenario file or false to create a new one.
	 * @param file If creating a new scenario file, this should be the name of the scenario file.
	 * If loading a scenario file, this should be the path to the scenario file to load.
	 * @return The Scenario instance instantiated after loading or creating the scenario file.
	 */
	public static Scenario loadOrCreate(boolean load, File file) {
		if(load) {
			
		} else {
			
		}
		return scenarioFile;
	}

}
