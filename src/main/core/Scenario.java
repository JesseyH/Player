package main.core;

import java.util.ArrayList;

public class Scenario {
	
	private static String fileName;
	private static String directory;
	private static int brailleCells;
	private static int buttons;
	
	private static ArrayList<String> scenarioBuffer;
	private static ArrayList<String> blockTextBuffer;
	private static ArrayList<String> blockButtonBuffer; 
	
	/**
	 * Initializes the Scenario by providing the name of the Scenario file
	 * as well as the directory where the scenario file will be saved to.
	 * @param filename The name of the scenario file.
	 * @param dir The directory where the scenario file and all supporting 
	 * files (i.e. audio files) will be saved.
	 */
	public static void initialize(String filename, String dir, int braillecells, int butt) {
		fileName = filename;
		directory = dir;
		brailleCells = braillecells;
		buttons = butt;
		scenarioBuffer = new ArrayList<>();
		blockTextBuffer = new ArrayList<>();
		blockButtonBuffer = new ArrayList<>();
	}
	
	/**
	 * Returns the fileName currently in use.
	 * @return The fileName
	 */
	public static String getFileName() {
		return fileName;
	}
	
	public static String getDirectory() {
		return directory;
	}
	
	public static int getBrailleCells() {
		return brailleCells;
	}
	
	public static int getButtons() {
		return buttons;
	}
	
	public static ArrayList<String> getScenarioBuffer() {
		return scenarioBuffer;
	}
	
	public static ArrayList<String> getBlockTextBuffer() {
		return blockTextBuffer;
	}
	
	public static ArrayList<String> getBlockButtonBuffer() {
		return blockButtonBuffer;
	}	

}
