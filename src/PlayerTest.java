import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
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
		String testString = "IF YOU HEAR THIS PLEASE PRESS Y. IF YOU HEAR THIS PLEASE PRESS Y.";
		Player.readText(testString);
		
		System.out.println("Did you hear the computer talking? Y/N");
		
		Scanner scan = new Scanner(System.in);
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
		Player.playSound("./resources/beep.wav");
		Player.playSound("./resources/beep.wav");
		
		System.out.println("Did you hear TWO beeps? Y/N");
		
		Scanner scan = new Scanner(System.in);
		if(!scan.nextLine().equalsIgnoreCase("y")) {
			fail("No beeps heard, therefore fail.");
		}
	}
	
	/**
	 * Test the doAction method
	 */
	@Test
	public void testDoAction() {
		HashMap<Integer, String[]> testCommand = new HashMap<>();
		String[] testSplit = "BUTTON 0=PLAY=./resources/beep.wav".split("="); // Splitting components of line by "="
		testCommand.put(Player.getButton(testSplit[0]), Player.getComponents(testSplit,1));	
		System.out.println("Did you hear ONE beep? Y/N");
		Scanner scan = new Scanner(System.in);
		if(!scan.nextLine().equalsIgnoreCase("y")) {
			fail("No beeps heard, therefore fail.");
		}
	}

}
