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
public class Player {
	static String lastRead;
	public static int buttonClicked;
	public static boolean accessed = false;
	static Simulator simulator = null; // Initializing Simulator instance
	static HashMap<Integer, String[]> commandAction = new HashMap<Integer, String[]>(); //Holds button-action relationship

	public static void main(String[] args) {
		BufferedReader fileReader = null; //BufferedReader to read from file line by line
		String line; //Variable "line" will be used for temporarily hold each line read from file
		String[] subStrings, subStringsB;
		PlayerB playerx = new PlayerB(); //Initializing action listener		
		
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
					if(Pattern.compile("<<.*>>").matcher(line).find()) {
						subStrings = line.split("	"); // Splitting line by tab spaces

						/*
						 * Detecting keywords in line and performing corresponding computations (i.e. <<COMMAND>>,
						 * 	 <<QUIZ>>, <<BRAILLE*>>, etc.)
						 */
						if(subStrings[0].contains("<<COMMAND>>")) {
							/*
							 * Stores button-to-action relationship in commandAction HashMap. Button ID is used as the 
							 * 	key, and array of components needed for action as the value. Action are performed when
							 * 	user clicks any relevant button.
							 */
							for (int i=1; i<subStrings.length;i++) {
								subStringsB = subStrings[i].split("=");
								commandAction.put(getButton(subStringsB[0]), getComponents(subStringsB,1));					
							}	
							
							while(true) {								
								/*
								 * Checking to see if button pressed is relevant for current command
								 */
								if (accessed && commandAction.get(buttonClicked)==null) {
									readText("No action associated with button"+buttonClicked+". Please try again!");
									accessed = false;
									continue;
								} else if (accessed && commandAction.get(buttonClicked)!=null) {
									break; // Exit loop to perform an action as relevant button is just clicked
								} else {
									/*
									 * Putting main thread to sleep for 100ms each time until user clicks a button.
									 */
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										System.out.println("Error occured while putting main thread to sleep");
									}
								}
							}
							doAction(commandAction); // Performs an action corresponding to button clicked
							resetConfig();
						} else if(subStrings[0].contains("<<CELLS>>")) {
							/*
							 * Initializes simulator using number of cells and buttons provided in input file
							 */
							simulator = new Simulator(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[3]));
							for (int i=0; i<Integer.parseInt(subStrings[3]);i++) {
								simulator.getButton(i).addActionListener(playerx);
							}
						} else if(subStrings[0].contains("<<SFX=")) {
							/*
							 * Used for playing in-line SFX with or without having text on left, right or both sides.
							 */
							String textToRepeatTemp = line;
							while(textToRepeatTemp.indexOf("<<")>=0) {
								String play = textToRepeatTemp.substring(textToRepeatTemp.indexOf("<<SFX=")
										+ 6, textToRepeatTemp.indexOf(">>"));
								try {
									String read = textToRepeatTemp.substring(0, textToRepeatTemp.indexOf("<<SFX=") - 1);
									readText(read);
								} catch (StringIndexOutOfBoundsException e) {
									// Non-harming StringIndexOutOfBoundsException is ignored
								}
								playSound(play);
								textToRepeatTemp = textToRepeatTemp.substring(textToRepeatTemp.indexOf(">>") + 1, 
										textToRepeatTemp.length());		
							}
						} else if(subStrings[0].contains("<<QUIZ>>")) {
							/*
							 * Compares user answer with correct answer and plays appropriate response message.
							 */
							resetConfig();
							int trials = 0, tries = 0;
							for (int i=1; i<subStrings.length;i++) {
								subStringsB = subStrings[i].split("=");
								if (i==1) {									
									commandAction.put(-1, new String[] {subStringsB[1]});
								} else if (i==2) {									
									trials = Integer.parseInt(subStringsB[1]) - 1;
								} else {												
									commandAction.put(getButton(subStringsB[0]), new String[] {subStringsB[1]});
								}
							}
							while(true) {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									System.err.println("Error occured while putting main thread to sleep");
								}
								try {
									if (accessed && commandAction.get(buttonClicked)[0].equals(commandAction.get(-1)[0])) {
										readText("Your answer is correct");
										break;
									} else if (accessed)  {
										if (trials>0 && trials<=tries) {
											readText("You were unable to guess the correct answer");
											break;
										}
										readText("Please try again");
										tries++;
										accessed=false;
									}
								} catch (NullPointerException e) {
									readText("There is no action associated with this button");
									accessed=false;
								}
							}
						} else if(subStrings[0].contains("<<BRAILLE")) {
							subStringsB = subStrings[0].split("=");
							int braille = Integer.parseInt(subStringsB[1]);
							String state = subStringsB[2].replace(">","");
							simulator.getCell(braille).displayCharacter(state.charAt(0));
						} 
					} else {
						readText(line);
					}
					if (line.length()>0) lastRead=line;
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
	 * Returns an array containing the components required to perform corresponding action
	 * 	i.e. For the instruction--BUTTON 1=BRAILLE=3=B-- 3 and B will be stored as
	 * 	components in an array with same order.
	 */
	public static String[] getComponents(String[] subString, int startValue) {
		String[] components = new String[5];
		for(;startValue<subString.length;startValue++) {
			components[startValue-1] = subString[startValue];
		}	
		return components;
	}
	
	/*
	 * 
	 * Performs action based on instruction codes:
	 * PLAY - players SFX file using path provided (i.e. BUTTON 0=PLAY=./resources/beep.wav)
	 * CONTINUE - Wait for user to press button corresponding to CONTINUE instruction and continues to read next line
	 * REPEAT - repeats the line that was just read
	 * REPATB - repeats text provided after "REPEATB=". (i.e. REPEATB=HELLO WORLD; for this "HELLO WORLD will be repeated)
	 * REPATC - repeats components of the last read line that are enclosed by <>;
	 * 	i.e. REPEATC command on line - "Hello <WORLD> IS <COOL>" will repeat "WORLD" and "COOL"
	 * BRAILLE - Used for setting the braille cells state. (i.e. BUTTON 1=BRAILLE=3=B; When button 1 is clicked
	 * braille cell 3 will be set to display character B)
	 * 
	 */
	public static void doAction(HashMap<Integer, String[]> commandAction) {
		switch(commandAction.get(buttonClicked)[0]){
		case "PLAY": {
			playSound(commandAction.get(buttonClicked)[1]);
			break;
		}
		case "CONTINUE": {
			break;
		}
		case "REPEAT": {
			System.out.println(commandAction.get(buttonClicked)[0] +" - "+lastRead);
			readText(lastRead);
			break;
		}
		case "REPEATB": {
			readText(commandAction.get(buttonClicked)[1]);
			break;
		}
		case "REPEATC": {
			repeatSub(lastRead);
			break;
		}
		case "BRAILLE": {
			simulator.getCell(Integer.parseInt(commandAction.get(buttonClicked)[1]))
			.displayCharacter(commandAction.get(buttonClicked)[2].charAt(0));
			readText("Braille cell "+commandAction.get(buttonClicked)[1]+" is set to display "+commandAction.get(buttonClicked)[2]);
			break;
		}
		default: {
			System.out.println("Unknown command - "+commandAction.get(buttonClicked)[0]);
			break;
		}
		}
	}
	
	/*
	 * Resets previous used configuration for reuse
	 */
	public static void resetConfig() {
		Player.accessed = false;
		Player.commandAction.clear();
	}
	
	/*
	 * Extracts button number as an integer from instruction (i.e. input to method - BUTTON 1; output - 1)
	 */
	public static int getButton(String str) {
		return Integer.parseInt(str.replaceAll("[^0-9]", ""));
	}
	
	/*
	 * Reads string argument provided using FreeTTS
	 */
	public static void readText(String textToRead) {
		if (textToRead.length()>0) Speak.textToSpeech(textToRead);
	}
	
	/*
	 * Plays SFX located at the path provided to method
	 */
	public static void playSound(String filepath) {
		Speak.playSound(filepath);
	}

	/*
	 * Repeats the line that was just read by FreeTTS
	 */
	public static void repeatLast(String textToRepeat) {
		Speak.textToSpeech(textToRepeat);
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
class PlayerB implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Player.buttonClicked = Integer.parseInt(e.getActionCommand());
		Player.accessed = true;
	}

}
