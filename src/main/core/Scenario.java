package main.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Static class which manages everything to do with the scenario file currently being worked on.
 * 
 * This class manages a scenario buffer and two block buffers. The scenario buffer is an array list containing
 * all the lines of the scenario file that will be written to the .txt file upon pressing the save button.
 * 
 * The two block buffers are the "blockTextBuffer" along with the "blockButtonBuffer". These buffers store the text
 * for the section being worked on through the "Scenario Section Builder". The "blockTextBuffer" stores all text for the 
 * section currently being worked on that does not pertain to button presses (e.g. setting voice, skipping, setting braille cells, etc).
 * The "blockButtonBuffer" stores all the text for the section currently being worked on that only pertains to button actions.
 * @author Group 6
 *
 */
public class Scenario {
	
	/**
	 * Declare all class variables
	 */
	private static String fileName;
	private static String directory;
	private static int brailleCells;
	private static int buttons;
	
	private static String header;
	private static ArrayList<String> scenarioBuffer;
	private static ArrayList<String> blockTextBuffer;
	private static ArrayList<String> blockButtonBuffer; 
	private static ArrayList<String[]> missingSections;
	
	/**
	 * Initializes the Scenario by providing the name of the Scenario file,
	 * the directory where the scenario file will be saved to, the number of braille cells,
	 * and the number of buttons available.
	 * @param filename The name of the scenario file.
	 * @param dir The directory where the scenario file and all supporting
	 * files (i.e. audio files) will be saved.
	 * @param braillecells The number of braille cells that will be used for this scenario.
	 * @param butt The number of buttons that will be used for this scenario
	 */
	public static void initialize(String filename, String dir, int braillecells, int butt) {
		fileName = filename;
		directory = dir;
		brailleCells = braillecells;
		buttons = butt;
		scenarioBuffer = new ArrayList<>();
		blockTextBuffer = new ArrayList<>();
		blockButtonBuffer = new ArrayList<>();
		missingSections = new ArrayList<>();
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
	
	/**
	 * Returns a string containing the absolute path to the directory
	 * that is currently being used to save this scenario file.
	 * @return The absolute path of the current directory in use.
	 */
	public static String getDirectory() {
		return directory;
	}
	
	/**
	 * Returns an int representing the number of braille cells being used
	 * for this scenario.
	 * @return The number of braille cells.
	 */
	public static int getBrailleCells() {
		return brailleCells;
	}
	
	/**
	 * Returns an int representing the number of buttons being used
	 * for this scenario.
	 * @return The number of buttons.
	 */
	public static int getButtons() {
		return buttons;
	}
	
	public static String getHeader() {
		return header;
	}
	
	/**
	 * Writes the disp-string command to the blockTextBuffer so that
	 * text can be shown across the braille cells.
	 * @param text The text to be shown across the braille cells
	 * @return True if the disp-string command was successfully written to the blockTextBuffer. False if otherwise.
	 */
	public static boolean bufferWriteBraille(String text) {
		if(blockTextBuffer != null) {
			if(text.length() <= buttons) {
				blockTextBuffer.add("/~disp-string:" + text);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Writes the given text to the blockTextBufer so that the text can be spoken by the computer.
	 * @param text Text that will be synthesized to speech.
	 * @return True if the text was successfully added to the blockTextBuffer/
	 */
	public static boolean bufferWriteTTS(String text) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add(text);
			return true;
		}
		return false;
	}
	
	/**
	 * Writes the sound command to the blockTextBuffer for the given sound file.
	 * Sound file must already be within the AudioFiles folder.
	 * @param soundFile The soundfile name plus extension to be played.
	 * @return Returns true if the sound command was successfully written.
	 */
	public static boolean bufferWriteSound(String soundFile) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add("/~sound:"+soundFile);
			return true;
		}
		return false;
	}
	
	/**
	 * Writes the skip command to the blockTextBuffer for the given section.
	 * @param section Section that the skip command will skip to.
	 * @return True if the skip command was successfully written.
	 */
	public static boolean bufferWriteSkip(String section) {
		if(blockTextBuffer != null) {
			blockTextBuffer.add("/~skip:"+section);
			return true;
		}
		return false;
	}
	
	/**
	 * Writes the set voice command to the beginning of the buffer for the current section.
	 * @param voice An integer between 1 and 4 inclusive that represents the available voices.
	 * @return True if the voice was successfully set.
	 */
	public static boolean bufferWriteVoice(int voice) {
		if(blockTextBuffer != null) {
			if(voice >= 1 && voice <= 4) {	//Only set the voice if the voice is between 1 and 4 as these are the only voices that exist
				ArrayList<String> newBuffer = new ArrayList<>();
				newBuffer.add("/~set-voice:"+voice);
				for(String s : blockTextBuffer) {
					newBuffer.add(s);
				}
				blockTextBuffer = newBuffer;
				return true;
			} else {	//If a voice that doesn't exist is trying to be set, we will return false as this cannot happen.
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Writes the repeat command to the top of the blockTextBuffer and the endrepeat 
	 * command to the end of the blockTextBuffer.
	 * @return True if the repeat and endrepeat commands were succesfull written to the blockTextBuffer.
	 */
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
	 * Writes the section name to the top of the blockTextBuffer.
	 * @param header The name of the section.
	 * @return True if the section was succesfully written to the top of the blockTextBuffer.
	 */
	public static boolean bufferWriteSectionHeader(String header) {
		Scenario.header = header;
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
	
	/**
	 * Writes a button action to the blockButtonBuffer for the given button. The button actions
	 * make a button skip to the specified section.
	 * @param button The button to enable functionality to.
	 * @param section The section that the button will skip to.
	 * @return True if the skip-button command was added.
	 */
	public static boolean bufferWriteSkipButtonAction(int button, String section) {
		if(blockButtonBuffer != null) {
				blockButtonBuffer.set(button, "/~skip-button:"+ button + " " +section);
			return true;
		}
		return false;
	}
	
	/**
	 * Write the repeat-button command to the blockButtonBuffer.
	 * @param button The button to write the repeat-button command for.
	 * @return True if the command was succefully added to the blockButtonBuffer.
	 */
	public static boolean bufferWriteReplayButtonAction(int button) {
		if(blockButtonBuffer != null) {
				blockButtonBuffer.set(button, "/~repeat-button:"+button);
			return true;
		}
		return false;
	}
	
	/**
	 * Clears the blockTextBuffer.
	 * @return True if the blockTextBuffer was successfully cleared.
	 */
	public static boolean clearBlockTextBuffer() {
		if(blockTextBuffer != null) {
			blockTextBuffer.clear();
			return true;
		}
		return false;
	}
	
	/**
	 * Clears the blockButtonBuffer.
	 * @return True if the blockButton buffer was successfully cleared.
	 */
	public static boolean clearBlockButtonBuffer() {
		if(blockButtonBuffer != null) {
			blockButtonBuffer.clear();
			for(int i = 0; i < buttons; i ++) {
				blockButtonBuffer.add("");
			}
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
		boolean hasButtons = false;
		for(String s: blockButtonBuffer) {
			if(s.length() > 0) {
				hasButtons = true;
				break;
			}
		}
		if(hasButtons) { 	//if there is button actions
			scenarioBuffer.add("/~reset-buttons"); //reset all buttons first
			for (String j : blockButtonBuffer) {	// Loop through all button options
				if(j.length() != 0)
				scenarioBuffer.add(j);
			}
			scenarioBuffer.add("/~user-input"); // end the user input section
		}
		scenarioBuffer.add(""); //Insert empty string so a blank line can be written inbetween sections. 
		return true;
	}
	
	/**
	 * Writes the scenarioBuffer to the scenario text file.
	 * @return True if the scenarioBuffer was successfully written to the scenario text file.
	 */
	public static boolean saveScenarioToFile() {
		if(scenarioBuffer.size() > 0) {
			
			try {
				FileWriter fw = new FileWriter(directory + "\\" + fileName + "\\" + fileName + ".txt");
				fw.write("Cells " + brailleCells); 	//Write the braille cells to the file.
				fw.write(System.getProperty("line.separator"));
				fw.write("Button " + buttons);		//Write the buttons to the file.
				fw.write(System.getProperty("line.separator"));
				for(String s : scenarioBuffer) {
					fw.write(s);
					fw.write(System.getProperty("line.separator"));
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the scenario buffer.
	 * @return The scenarioBuffer arraylist.
	 */
	public static ArrayList<String> getScenarioBuffer() {
		return scenarioBuffer;
	}
	
	/**
	 * Returns the block text buffer/
	 * @return The blockTextBuffer arraylist.
	 */
	public static ArrayList<String> getBlockTextBuffer() {
		return blockTextBuffer;
	}
	
	/**
	 * Returns the block button buffer.
	 * @return The blockButtonBuffer arraylist.
	 */
	public static ArrayList<String> getBlockButtonBuffer() {
		return blockButtonBuffer;
	}	
	
	/**
	 * Returns the missing Sections list
	 * @return the missing Sections arraylist
	 */
	public static ArrayList<String[]> getMissingSections() {
		return missingSections;
	}
	
	public static boolean isInMissing(String s) {
		boolean isMissing = false;
		for (String[] missing : missingSections) {
			if (s.equals(missing[0])) {
				isMissing = true;
			}
		}
		return isMissing;
	}

}
 