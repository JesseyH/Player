package main.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
		createScenarioDirectory();
	}
	
	/**
	 * Creates the scenario directory along with the AudioFiles sub directory
	 * contained within the scenario directory.
	 * @return True if the scenario directory was created.
	 */
	public static boolean createScenarioDirectory() {
		boolean success = false;
		try {
			String newDir = directory + "\\" + fileName + "\\" + "AudioFiles";
			File scenarioDirectory = new File(newDir);
			success = scenarioDirectory.mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
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
	
	public static boolean bufferWriteBraille(String text) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add("/~disp-string:" + text);
			return true;
		}
		return false;
	}
	
	public static boolean bufferWriteTTS(String text) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add(text);
			return true;
		}
		return false;
	}
	
	public static boolean bufferWriteSound(String soundFile) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add("/~sound:"+soundFile);
			return true;
		}
		return false;
	}
	
	public static boolean bufferWriteSkip(String section) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add("/~skip:"+section);
			return true;
		}
		return false;
	}
	
	public static boolean bufferWriteSectionHeader(String header) {
		if(blockTextBuffer != null) {
			ArrayList<String> newBuffer = new ArrayList<>();
			newBuffer.add("/~" + header);
			for(String s : blockTextBuffer) {
				newBuffer.add(s);
			}
			blockTextBuffer = newBuffer;
			return true;
		}
		return false;
	}
	
	public static boolean bufferWriteReplay() {
		if(blockTextBuffer != null) {
			ArrayList<String> newBuffer = new ArrayList<>();
			newBuffer.add("/~repeat");
			for(String s : blockTextBuffer) {
				newBuffer.add(s);
			}
			newBuffer.add("/~endrepeat");
			blockTextBuffer = newBuffer;
			return true;
		}
		return false;
	}
	
	/**
	 * Copy's an audio file into the "AudioFile" directory
	 * contained within the same directory as the scenario file.
	 * @param audioFile The audio file to copy.
	 * @return Returns true if the copy was successful and false if otherwise.
	 */
	public static boolean addAudioFile(File audioFile) {
		boolean success = false;
		try {
			File destination = new File(directory + "\\" + fileName + "\\" + "AudioFiles" + "\\" + audioFile.getName());
			FileInputStream inputStream = new FileInputStream(audioFile);
			FileOutputStream outputStream = new FileOutputStream(destination);
			
			FileChannel sourceChannel = inputStream.getChannel();
			FileChannel destChannel = outputStream.getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			sourceChannel.close();
			destChannel.close();
			
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			success = true;
		}
		return success;
	}
	
	public static boolean bufferWriteSkipButtonAction(int button, String section) {
		if(blockButtonBuffer != null) {
			if(blockButtonBuffer.size() == 0 && button == 0) {
				blockButtonBuffer.add(button, "/~skip-button:"+ button + " " +section);
			} else if(blockButtonBuffer.size() <= button) {
				blockButtonBuffer.add(button, "/~skip-button:"+ button + " " +section);
			} else {
				blockButtonBuffer.set(button, "/~skip-button:"+ button + " " +section);
			}
			return true;
		}
		return false;
	}
	
	public static boolean bufferWriteReplayButtonAction(int button) {
		if(blockButtonBuffer != null) {
			if(blockButtonBuffer.size() == 0 && button == 0) {
				blockButtonBuffer.add(button, "/~repeat-button:"+button);
			} else if(blockButtonBuffer.size() <= button) {
				blockButtonBuffer.add(button, "/~repeat-button:"+button);
			} else {
				blockButtonBuffer.set(button, "/~repeat-button:"+button);
			}
			return true;
		}
		return false;
	}
	
	public static boolean clearBlockTextBuffer() {
		if(blockTextBuffer != null) {
			blockTextBuffer.clear();
			return true;
		}
		return false;
	}
	
	public static boolean clearBlockButtonBuffer() {
		if(blockButtonBuffer != null) {
			blockButtonBuffer.clear();
			return true;
		}
		return false;
	}
	
	/**
	 * This method copy's the data from the block buffers (created during section creation)
	 * into the scenario files buffer.
	 * @return
	 */
	public static boolean copyBlockBuffersToScenarioBuffer() {
		for(String s : blockTextBuffer) {
			scenarioBuffer.add(s);
		}
		if(blockButtonBuffer.size() > 0) { 	//if there is button actions
			scenarioBuffer.add("/~reset-buttons"); //reset all buttons first
			for (String j : blockButtonBuffer) {	// Loop through all button options
				scenarioBuffer.add(j);
			}
			scenarioBuffer.add("/~user-input"); // end the user input section
		}
		scenarioBuffer.add(""); //Insert empty string so a blank line can be written inbetween sections. 
		return true;
	}
	
	public static boolean saveScenarioToFile() {
		if(scenarioBuffer.size() > 0) {
			
			try {
				FileWriter fw = new FileWriter(directory + "\\" + fileName + "\\" + fileName + ".txt");
				for(String s : scenarioBuffer) {
					fw.write(s);
					fw.write(System.getProperty( "line.separator"));
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
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
 