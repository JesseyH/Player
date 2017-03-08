import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * This class is included as an example of how to use the classes in the simulator package.
 * 
 */
public class PlayerX {
	static String lastRead;
	public static int buttonClicked;
	public static boolean accessed = false;
	static Simulator simulator = null; // Initializing Simulator instance
	static HashMap<String, String[]> commandAction = new HashMap<String, String[]>(); //Holds button-action relationship

	public static void main(String[] args) {
		BufferedReader fileReader = null; //BufferedReader to read from file line by line
		String line; //Variable "line" will be used for temporarily hold each line read from file
		PlayerC playerx = new PlayerC(); //Initializing action listener		

		try {
			/*
			 * Opens file for reading
			 * Throws missing file exception
			 */
			fileReader = new BufferedReader(new FileReader(args[0])); 

			try {
				/*
				 * Reading input file line by line
				 */
				while((line = fileReader.readLine())!=null) {					
					if(isCommand("<<COMMAND>",line)) {
						buildMap(line);
						while (true) {
							doAction(getString(getButtonPressed()));
							resetConfig();
						}
					} else if(isCommand("<<QUIZ>>",line)) {
						buildMap(line);
						int trials = getInt(commandAction.get("TRIALS")[0]), tries = 0;
						while(true) {
							if (quizAssert(getString(getButtonPressed()))) {
								readText("Your answer is correct");
								break;
							} else if(tries>trials && trials>0) {
								readText("Your were unable to guess correct answer");
								break;
							} else {
								readText("Please try again");
							}
							tries++;
						}
					} else if(isCommand("<<SFX=.*>>",line)) {
						inlineSFX(line);
					} else if(isCommand("<<BRAILLE=.*>>",line)) {
						setBraille(line);
					} else if(isCommand("<<CELLS>>",line)) {
						String[] input = line.split("\t");
						initSimulator(getInt(input[1]),getInt(input[3]), playerx);
					} else {
						readText(line);
					}
				}

			} catch (IOException | ArrayIndexOutOfBoundsException e) {
				System.err.println("Error occured while reading the file. Please make sure correct format is used");
				e.printStackTrace();	
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error location the file");
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.err.println("Error closing file");
			}
		}
	}

	/*
	 * This method is used for playing in-line SFX within texts in same line (i.e. Firealarm <<SFX=./fire.wav>>)
	 */
	private static void inlineSFX(String line) {
		if(line.indexOf("<<")>=0) {
			String play = line.substring(line.indexOf("<<SFX=") + 6, line.indexOf(">>"));
			int endpoint = (line.indexOf("<<SFX=") - 1);
			String read = line.substring(0, (endpoint>=0) ? endpoint : 0);
			readText(read);
			playSound(play);
			inlineSFX(line.substring(line.indexOf(">>") + 1, 
					line.length()));
		} else if (line.length()>0) {
			readText(line);
		}
	}

	/*
	 * Sets braille cells without user interaction (i.e. <<BRAILLE=2=A>> will set braille cell 2 to display A)
	 */
	private static boolean setBraille(String line) {
		String[] temp = line.split("=");
		simulator.getCell(getInt(temp[1])).displayCharacter(temp[2].replace(">", "").charAt(0));
		return false;				
	}

	/*
	 * initSimulator is used for initializing simulator with given number of buttons and cell. In addition, this method
	 * 	adds action listener "playerx" to each buttons.
	 */
	public static void initSimulator(int cellls, int buttons, PlayerC playerx) {
		simulator = new Simulator(cellls,buttons);
		for(int i=0;i<buttons;i++) {
			simulator.getButton(i).addActionListener(playerx);
		}
	}

	/*
	 * Used for obtaining which button was pressed by user
	 */
	public static int getButtonPressed(){
		while(true) {	
			if (accessed && commandAction.containsKey(getString(buttonClicked))) {
				accessed=false;
				return buttonClicked;
			} else if(accessed) {
				readText("No action associated with button "+buttonClicked);
				accessed=false;
			} else {	
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("Error occured while putting main thread to sleep");
				}
			}
		}
	}


	/*
	 * Return true if current line has command provided as first argument
	 */
	public static boolean isCommand(String string, String line)
	{
		return Pattern.compile(string).matcher(line).find();
	}

	/*
	 * Perform action based on instruction codes and button pressed:
	 * 	PLAY - Plays SFX file (i.e. BUTTON 0=PLAY=./hi.wav, clicking button 0 will play hi.wav)
	 * 	Continue - continue to reading the next line in input file
	 */
	public static void doAction(String buttonID) {

		switch(commandAction.get(buttonID)[0]){
		case "PLAY": {
			playSound(commandAction.get(buttonID)[1]);
			break;
		}
		case "CONTINUE": {
			break;
		}
		case "REPEAT": {
			System.out.println(commandAction.get(buttonID)[0] +" - "+lastRead);
			readText(lastRead);
			break;
		}
		case "REPEATB": {
			readText(commandAction.get(buttonID)[1]);
			break;
		}
		case "REPEATC": {
			repeatSub(lastRead);
			break;
		}
		case "BRAILLE": {
			simulator.getCell(Integer.parseInt(commandAction.get(buttonID)[1]))
			.displayCharacter(commandAction.get(buttonID)[2].charAt(0));
			readText("Braille cell "+commandAction.get(buttonID)[1]+" is set to display "+commandAction.get(buttonID)[2]);
			break;
		}
		default: {
			readText("Unknown Command");
			break;
		}
		}
	}

	/*
	 * buildMap method is used for adding button-to-action relationship in HashMap
	 */
	public static void buildMap(String line) {
		String[] subString = line.split("\t"), subStringB, finalString;
		for (int i=1;i<subString.length;i++) {	
			subStringB = subString[i].split("=");
			String key = getKey(subStringB[0]);
			finalString = new String[subStringB.length-1];
			for (int j=1;j<subStringB.length;j++) {
				finalString[j-1] = subStringB[j];
			}
			commandAction.put(key, finalString);
		}
	}

	/*
	 * Converts string to integer
	 */
	public static int getInt(String value) {
		return Integer.parseInt(value);
	}
	
	/*
	 * Converts integer to string
	 */
	public static String getString(int value) {
		return Integer.toString(value);
	}

	/*
	 * Returns true if answer corresponding to button clicked is equals correct answer, and false otherwise.
	 */
	public static boolean quizAssert(String buttonID) {
		return commandAction.get("ANSWER")[0].equals(commandAction.get(buttonID)[0]);
	}

	/*
	 * Resets previous used configuration for reuse
	 */
	public static void resetConfig() {
		PlayerX.accessed = false;
		PlayerX.commandAction.clear();
	}

	/*
	 * Extracts button number as an integer from instruction (i.e. input to method - BUTTON 1; output - 1)
	 */
	public static String getKey(String str) {
		String val = str.replaceAll("[^0-9]", "");
		return (val.length()!=0) ? val : str;
	}

	/*
	 * Reads string argument provided using FreeTTS
	 */
	public static void readText(String textToRead) {
		if (textToRead.length()>0) {
			Speak.textToSpeech(textToRead);
			lastRead = textToRead;
		}
	}

	/*
	 * Plays SFX located at the path provided to method
	 */
	public static void playSound(String filepath) {
		Speak.playSound(filepath);
	}

	/*
	 * Repeats part of last line read enclosed within < > tags
	 */
	public static void repeatSub(String textToRepeat) {
		String textToRepeatTemp = textToRepeat;
		while(textToRepeatTemp.indexOf("<")>0) {
			String repeat = textToRepeatTemp.substring(textToRepeatTemp.indexOf("<") + 1, textToRepeatTemp.indexOf(">"));
			Speak.textToSpeech(repeat);
			textToRepeatTemp = textToRepeatTemp.substring(textToRepeatTemp.indexOf(">") + 1, textToRepeatTemp.length());		
		}
	}
}

/*
 * ActionListener for all the buttons
 * Sets variable buttonClicked and accessed which is used for continuing paused main method
 * 	and to perform action that user requested by clicking specific button.
 */
class PlayerC implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		PlayerX.buttonClicked = Integer.parseInt(e.getActionCommand());
		PlayerX.accessed = true;
	}

}
