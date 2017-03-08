import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerTedi {
	
	private static Simulator simulator;
	private static ArrayList<String> lines;
	private static int buttons, cells;
	
	/**
	 * Main method that gets the program started.
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		System.out.println("Please enter the file you would like to load (E.g input.txt):");
		Scanner scan = new Scanner(System.in);	//Create scanner object.
		String fileToLoad = scan.nextLine();	//Grab user input representing the file they would like to load.
		
		loadFileIntoArrayList(fileToLoad);		//Load the file into the lines array list.
		initializeSimulator();					//Initialize the simulator.
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
		} else if(!lines.isEmpty()) {	//If lines array list isn't null, check if its empty.
			lines.clear();				//If the lines array list isn't empty, clear it so it is.
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
					e.printStackTrace();					//Print the stack trace of the exception.
				}
			}
		} else {											//If the first line did not contain the "<define>" command, then thrown an exception.
			try {											//Start try-catch block.
				throw (new Exception("The line that defines # of buttons and # of braille cells does not exist!"));	//Throw exception.
			} catch(Exception e) {							//Catch the exception.
				e.printStackTrace();						//Print the stack trace of the exception.
			}
		}
	}
	
	/**
	 * Gets the lines array list or throws an exception
	 * if the lines array list is null or empty.
	 * @return The lines array list.
	 */
	public static ArrayList<String> getLines() {
		if (lines != null || lines.isEmpty()) {	//Check if the lines array list is empty or null.
			try {								//Begin try-catch block.
				throw (new Exception());		//Throw an exception if the list is empty or null.
			} catch(Exception e) {				//Catch the thrown exception.
				e.printStackTrace();			//Print the stack trace of the exception.
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
