import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

/**
 * Tests the player class.
 * @author Group 6
 *
 */
public class PlayerTest {

	/**
	 * This tests the getComponents method by simulating input
	 * that would be read from a file and passing it to the get
	 * components method. The string array returned is then analyzed
	 * to ensure that it contains the correct strings.
	 */
	@Test
	public void testGetComponents() {
		//Create the string that will be tested and split it based on '=" delimiter (similar to player file)
		String[] testSplit = "BUTTON 0=BRAILLE=4=A".split("=");
		
		//Call the get components method 
		String[] toTest = Player.getComponents(testSplit, 1);
		
		//Create a scoring system
		int score = 0;
		
		//Loop over all indices in the toTest array.
		for(String curr : toTest) {
			if(curr != null) {
				
				//If any of the following words are found, increase the score counter.
				switch (curr) {
				case "BRAILLE":
					score++;
					break;
				case "4":
					score++;
					break;
				case "A":
					score++;
					break;
				}
			}
		}
		
		//A score of 3 means the test is a pass
		if(score != 3) {
			fail("Get components method is NOT working properly!");
		}
	}
	
	/**
	 * This tests the getButton method by simulating input that would
	 * be read from an input file. This method ensures that when getButton
	 * is passed a string such as "BUTTON 1", the number 1 is returned.
	 */
	@Test
	public void testGetButton() {
		String testString = "BUTTON 10";
		assertEquals(10, Player.getButton(testString));
	}
	
	/**
	 * Tests the readText method by calling it and asking the user
	 * if they heard the computer speak. If the user enters anything except y for yes,
	 * the test is a fail.
	 */
	@Test
	public void testReadText() {
		//Create a test string to run through the TTS engine.
		String testString = "IF YOU HEAR THIS PLEASE PRESS Y. IF YOU HEAR THIS PLEASE PRESS Y.";
		Player.readText(testString);
		
		//Ask user if they heard input.
		System.out.println("Did you hear the computer talking? Y/N");
		
		//Take input from console.
		Scanner scan = new Scanner(System.in);
		
		//If input is not Y for yes, its a fail.
		if(!scan.nextLine().equalsIgnoreCase("y")) {
			fail("If you could not hear the computer speak, its a fail!");
		}
	}
	
	/**
	 * Tests the playSound method by calling it and asking the user
	 * if they heard beeping sounds. If the user enters anything except y for yes,
	 * the test is a fail.
	 */
	@Test
	public void testPlaySound() {
		//Play the test sound twice. This should play two beeps.
		Player.playSound("./resources/beep.wav");
		Player.playSound("./resources/beep.wav");
		
		//Ask user if they heard beeps.
		System.out.println("Did you hear TWO beeps? Y/N");
		
		//Read in input from console.
		Scanner scan = new Scanner(System.in);
		
		//Check if input is Y for yes.
		if(!scan.nextLine().equalsIgnoreCase("y")) {
			fail("No beeps heard, therefore fail.");
		}
	}
	
	/**
	 * Tests the doAction method by creating a command and passing it to the
	 * doAction method. A command is a hashMap in which an integer representing the
	 * button number is mapped to a string representing the command.
	 * In this test, the command is to play a sound and ask the user if they heard it.
	 */
	@Test
	public void testDoAction() {
		//Create a hash map similar to the one that would be created when parsing the input file.
		HashMap<Integer, String[]> testCommand = new HashMap<>();
		
		//Simulate a test command from the input file to play a sound.
		String[] testSplit = "BUTTON 0=PLAY=./resources/beep.wav".split("="); // Splitting components of line by "="
		
		//Check to  make sure that calling getButton on the string will return 0.
		assertEquals(0, Player.getButton(testSplit[0]));
		
		//Push the command number mapped to the command into the testCommand hash map.
		testCommand.put(Player.getButton(testSplit[0]), Player.getComponents(testSplit,1));	
		
		//Call the doAction method with the testCommand hash map.
		Player.doAction(testCommand);
		
		//Ask user if they heard the noise.
		System.out.println("Did you hear ONE beep? Y/N");
		
		//Read in input from console.
		Scanner scan = new Scanner(System.in);
		
		//Check if input is Y for yes.
		if(!scan.nextLine().equalsIgnoreCase("y")) {
			fail("No beeps heard, therefore fail.");
		}
	}

}
