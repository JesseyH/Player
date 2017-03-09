import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class reads a Story file and displays it to a Braille Simulator
 * <p>
 * The main method reads the story file and stores it to an Arraylist of 
 * type string then it initializes the simulator with the size information
 * obtained from the story file. It then runs the story.
 * @author Group 6
 *
 */
public class Player {
	protected static Simulator simulator;
	protected static ArrayList<String> lines;
	protected static int buttons, cells;
	protected static int index = 1;
	protected static OptionHandler l = new OptionHandler();
	
	/**
	 * loads the story into an ArrayList, initializes the simulator and runs the story.
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println("Please enter the file you would like to load (E.g input.txt):");
		Scanner scan = new Scanner(System.in);	//Create scanner object.
		String fileToLoad = scan.nextLine();	//Grab user input representing the file they would like to load.
		scan.close();
		loadFileIntoArrayList(fileToLoad);		//Load the file into the lines array list.
		initializeSimulator();					//Initialize the simulator.
		
		//parses the first index through the getCommand function. Starts the story.
		getCommand(getLines().get(index));
	}
	
	/**
	 * Separates the input string into the command and the input it then sends
	 * the input to a method to handle the information.
	 * 
	 * @param s the line being parsed for it's command and information
	 * @return
	 */
	public static void getCommand(String s){
		String split[] = new String[2];
		split = s.split(",", 2);
		System.out.println("CURRENT COMMAND: " + s);
		if (split[0].equals("<SFX>")) {
			sfx(split[1]);
		} else if (split[0].equals("<TTS>")) {
			tts(split[1]);
		} else if (split[0].equals("<OPTION>")) {
			option(split[1]);
		} else if (split[0].equals("<DISPLAY>")) {
			display(split[1]);
		} else if (split[0].equals("<JUMP>")) {
			jump(split[1]);
		} else {
			try {
				throw (new Exception("COMMAND WAS NOT FOUND!"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			next();
		}
	}
	
	/**
	 * moves the pointer of the index to the next line
	 */
	public static void next() {
		index += 1;
		if(index >= getLines().size()) {
			System.out.println("End of file.");
			System.exit(0);
		}
		getCommand(getLines().get(index));
	}
	
	/**
	 * plays a wav file
	 * @param fileName the file name of the .wav file to be played
	 * @pre the fileName is a valid file of type .wav
	 */
	public static void sfx(String fileName) {
		Speak.playSound(fileName);
		next();
	}
	
	/**
	 * Sends the text to the textToSpeech method in Speak which converts 
	 * the string into speech
	 * @param text the text to be converted into speech
	 */
	public static void tts(String text) {
		Speak.textToSpeech(text);
		next();
	}
	
	/**
	 * assigns the buttons with the correct jump locations and gives it an
	 * actionListener
	 * @param s string that contains the jump locations
	 */
	public static void option(String s) {
		String jump[] = new String[buttons];
		jump = s.split(",", buttons);
		for (int i = 0; i < buttons; i++) {
			simulator.getButton(i).addActionListener(l);
			simulator.getButton(i).setActionCommand(jump[i]);
		}
	}
	
	/**
	 * displays a string to the cells on the simulator using the simulator
	 * app
	 * @param aString string to be displayed
	 */
	public static void display(String aString) {
		System.out.println("STRING: " + aString);
		simulator.displayString(aString);
		next();
	}
	
	public static void jump(String j) {
		index = Integer.valueOf(j);
		getCommand(getLines().get(index));		
	}
	
	/**
	 * Loads a file into the "lines" array list. The array list
	 * will contain an entry for every line in the input file.
	 * A line is defined as all the text preceding the new line character ( \n ).
	 * @param toLoad The file to load into the array list.
	 */
	public static void loadFileIntoArrayList(String toLoad) {
		if(lines == null) {				//Check if the lines array list is null.
			lines = new ArrayList<>();	//If the array list is null, create a new instance.
		} else if(!getLines().isEmpty()) {	//If lines array list isn't null, check if its empty.
			getLines().clear();				//If the lines array list isn't empty, clear it so it is.
		}
		try {
			File file = new File(toLoad);				//Load the file into a file object.
			Scanner fileScanner = new Scanner(file);	//Create a scanner object that scans the file.
			while(fileScanner.hasNextLine()) {			//Loop over every line in the file
				String currentLine = fileScanner.nextLine();	//Get the current line.
				lines.add(currentLine);					//Append the current line to the array list.
			}
			fileScanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the simulator with the number of buttons and number of braille cells
	 * defined in the input file. The first line of the input file should always contain
	 * the definitions for number of buttons and number of braille cells. The definition
	 * line should look like this:
	 * <DEFINE>,#_OF_CELLS,#_OF_BUTTONS
	 */
	public static void initializeSimulator() {
		String definitionLine = getLines().get(0);			//Get the first line from the input file.
		definitionLine.toLowerCase();						//Convert the line to lower case for further parsing.
		
		if(definitionLine.contains("<define>")) {			//Check if the first line contains the "<define>" command.
			
			String[] split = definitionLine.split(",");		//Split the line based on the "," delimiter.
			
			if(split.length == 3) {							//Check if the split string has 3 elements to it. First element is "<define>",
															//second element should contain # of cells, third element should contain # of buttons.
				cells = tryParse(split[1]);					//Attempt to convert the second element (# of cells) to an integer.
				buttons = tryParse(split[2]);				//Attempt to convert the third element (# of btns) to to an integer.
				simulator = new Simulator(cells, buttons);	//Initialize the simulator object with the # of cells and # of btns.
			} else {										//If the split string does not have 3 elements to it, then an exception will be thrown.
				try {										//Start try-catch block.
					throw (new Exception("The simulator definition line does not have the right number of elements!"));	//Throw exception.
				} catch(Exception e) {						//Catch the thrown exception.
					e.printStackTrace();
					System.exit(3);							//Stop program execution.
				}
			}
		} else {											//If the first line did not contain the "<define>" command, then thrown an exception.
			try {											//Start try-catch block.
				throw (new Exception("The line that defines # of buttons and # of braille cells does not exist!"));	//Throw exception.
			} catch(Exception e) {							//Catch the exception.
				e.printStackTrace();
				System.exit(4);								//Stop program execution.
			}
		}
	}
	
	/**
	 * Gets the lines array list or throws an exception
	 * if the lines array list is null or empty.
	 * @return The lines array list.
	 */
	public static ArrayList<String> getLines() {
		if (lines == null) {					//Check if the lines array list is null.
			try {								//Begin try-catch block.
				throw (new Exception("The array list 'lines' is null."));		//Throw an exception.
			} catch(Exception e) {				//Catch the thrown exception.
				e.printStackTrace();			//Print the stack trace of the exception.
				System.exit(1);					//Exit the program with an error.
			}
		} else if (lines.isEmpty()) {			//Check if the lines array list is null. This means no lines were read from the file.
			try {								//Begin try-catch block.
				throw (new Exception("There are no lines contained within the input file."));		//Throw an exception.
			} catch(Exception e) {				//Catch the thrown exception.
				e.printStackTrace();			//Print the stack trace of the exception.
				System.exit(2);					//Exit the program with an error.
			}
		}
		return lines;							//If the lines array list isn't null or empty, return the lines array list.
	}
	
	/**
	 * Attempts to convert a string object to an integer object.
	 * @param text The string that contains the integer to be parsed.
	 * @return The integer that was parsed.
	 */
	public static Integer tryParse(String text) {
		try {								//Begin try-catch block.
			return Integer.parseInt(text);	//Attempt to convert the string to an integer and return the value.
		} catch (NumberFormatException e) {	//Catch any conversion exceptions.
			e.printStackTrace();			//Print the stack trace of the exception.
			return null;					//Return null if there was an exception with the conversion.
		}
	}
}

/**
 * Handles button clicks, if the button click is invalid it waits for another button click
 * once a valid option is entered it removes the actionlistener and sends the correct 
 * scenarioF line to be played by the getCommand function.
 * @author Group 6
 *
 */
class OptionHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		System.out.println(s);
		if (!s.equalsIgnoreCase("NULL")) {
			Player.index = Integer.valueOf(s);
			for (int i = 0;i < Player.buttons; i++){
				Player.simulator.getButton(i).removeActionListener(Player.l);
			}
			Player.getCommand(Player.getLines().get(Player.index));
		} else {
			Speak.textToSpeech("Not a Valid Option. Please Try again.");
		}
	}

}