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

	public static void main(String[] args) throws InterruptedException {
		BufferedReader fileReader = null; // BufferedReader to read from file line by line
		Simulator simulator = null; // Initializing Simulator instance
		String line; // Variable "line" will be used for temporarily hold each line read from file
		String[] subStrings, subStringsB; //subStrings is used for splitting string delimited by tab-space, subStringB by "="
		PlayerB playerx = new PlayerB(); // Initializing action listener class instance
		HashMap<Integer, String[]> commandAction = new HashMap<Integer, String[]>(); // This will used for holding action for command temporarily for a line

		try {
			/*
			 * Open file for reading
			 * Throws missing file exception
			 */
			fileReader = new BufferedReader(new FileReader(args[0])); 
			try {
				/*
				 * Reading input file line by line and performing respective action line-by-line
				 */
				while((line = fileReader.readLine())!=null) {
					if(Pattern.compile("<<.*>>").matcher(line).find()) { //Checking if line is contain command that requires addition work
						subStrings = line.split("	"); // Splitting line by tab spaces
						
						/*
						 * Detecting keywords in line and performing corresponding computation. (i.e. COMMAND, SFX, CELLS, etc.)
						 * 						 */
						if(subStrings[0].contains("<<COMMAND>>")) {
							for (int i=1; i<subStrings.length;i++) {
								subStringsB = subStrings[i].split("="); // Splitting components of line by "="
								
								if (subStringsB.length>2) {
									commandAction.put(getButton(subStringsB[0]), new String[] {subStringsB[1], subStringsB[2]});
								} else {
									commandAction.put(getButton(subStringsB[0]), new String[] {subStringsB[1]});
								}								
							}	
							/*
							 * Checking and waiting for actionPerformed method to execute before continuing to next line
							 */
							while(!accessed) {
								Thread.sleep(100);
							}
							/*
							 * Performing action 
							 */
							doAction(commandAction);
						} else if(subStrings[0].contains("<<CELLS>>")) {
							simulator = new Simulator(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[3]));
							for (int i=0; i<Integer.parseInt(subStrings[3]);i++) simulator.getButton(i).addActionListener(playerx);
						} else if(subStrings[0].contains("<<SFX=")) {
							String textToRepeatTemp = line;
							while(textToRepeatTemp.indexOf("<<")>=0) {
								String play = textToRepeatTemp.substring(textToRepeatTemp.indexOf("<<SFX=") + 6, textToRepeatTemp.indexOf(">>"));
								try {
									String read = textToRepeatTemp.substring(0, textToRepeatTemp.indexOf("<<SFX=") - 1);
									readText(read);
								} catch (StringIndexOutOfBoundsException e) {

								}
								playSound(play);
								textToRepeatTemp = textToRepeatTemp.substring(textToRepeatTemp.indexOf(">>") + 1, textToRepeatTemp.length());		
							}
						} else if(subStrings[0].contains("<<QUIZ>>")) {
							commandAction.clear();
							accessed = false;
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
								Thread.sleep(100);
								if (accessed && commandAction.get(buttonClicked)[0].equals(commandAction.get(-1)[0])) {
									readText("Correct");
									break;
								} else if (accessed)  {
									if (trials<0 || trials<=tries) {
										readText("You were unable to guess the correct answer");
										break;
									}
									readText("Try Again");
									tries++;
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
		default: {
			System.out.println("Unknown command - "+commandAction.get(buttonClicked)[0]);
			break;
		}
		}
	}
	public static int getButton(String str) {
		return Integer.parseInt(str.replaceAll("[^0-9]", ""));
	}
	public static void readText(String textToRead) {
		if (textToRead.length()>0) Speak.textToSpeech(textToRead);
	}

	public static void playSound(String filepath) {
		Speak.playSound(filepath);
	}

	public static void repeatLast(String textToRepeat) {
		Speak.textToSpeech(textToRepeat);
	}

	public static void repeatSub(String textToRepeat) {
		String textToRepeatTemp = textToRepeat;
		while(textToRepeatTemp.indexOf("<")>0) {
			String repeat = textToRepeatTemp.substring(textToRepeatTemp.indexOf("<") + 1, textToRepeatTemp.indexOf(">"));
			Speak.textToSpeech(repeat);
			textToRepeatTemp = textToRepeatTemp.substring(textToRepeatTemp.indexOf(">") + 1, textToRepeatTemp.length());		
		}
	}
}

class PlayerB implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Player.buttonClicked = Integer.parseInt(e.getActionCommand());
		Player.accessed = true;
	}

}
