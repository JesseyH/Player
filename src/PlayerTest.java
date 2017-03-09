
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Automated test cases for the Player class.
 * This class tets functions that do not required 
 * @author Group 6
 *
 */
public class PlayerTest {

	/**
	 * Setup the Player instance before testing.
	 */
	@BeforeClass
	public static void setupBefore() {
		Player.inTestMode = true;				//Put the player app into test mode. This disables some features that will break testing.
		Player.start("input.txt");				//Start the player app.
	}

	/**
	 * Tests the getCommand() method. It does this 
	 * by simulating commands that would typically be read in
	 * from the story file and testing the getCommand's return.
	 */
	@Test
	public void testGetCommand() {
		String sfxTest = "<SFX>,./resources/beep.wav";		//Simulate play sound affect command.
		String ttsTest = "<TTS>,TEST";						//Simulate text to speech command.
		String optionTest = "<OPTION>,2,5,NULL,NULL,NULL";	//Simulate the create option command.
		String displayTest = "<DISPLAY>,test";				//Simulate the display text command.
		String noCommandTest = "<NOCOMMAND>,hello";			//simulate calling a command that doesn't exist.
		assertEquals(0, Player.getCommand(sfxTest));		//Test <SFX> command.
		assertEquals(1, Player.getCommand(ttsTest));		//Test <TTS> command.
		assertEquals(2, Player.getCommand(optionTest));		//Test <OPTION> command.
		assertEquals(3, Player.getCommand(displayTest));	//Test <DISPLAY> command.
		assertEquals(4, Player.getCommand(noCommandTest));	//Test a command that doesnt exist (<NOCOMMAND>).
	}
	
	/*
	 * Test the getLines() method.
	 * It does this by ensuring that calling getLines() does
	 * not return null as well as making sure it is not empty.
	 */
	@Test
	public void testGetLines() {		
		ArrayList<String> toTest = Player.getLines();	//Call the getLines method.
		if(toTest == null) {							//Check if returned arrayList is null.
			fail("Player.getLines() returns null.");	//Fail the test if its null.
		} else if(toTest.isEmpty()) {					//Check if returned araryList is empty.
														//Fail the test case if the returned arrayList is empty.
			fail("Player.getLines() returns an empty list. Implies somthing went wrong with file loading/reading.");
		}
	}
	
	/**
	 * Test the sfx() method.
	 * It does this by ensuring the sfx() method returns true.
	 */
	@Test
	public void testSFX() {
		assertEquals(true, Player.sfx("./resources/beep.wav"));
	}
	
	/**
	 * Test the tts() method.
	 * It does this by ensuring the tts() method returns true.
	 */
	@Test
	public void testTTS() {
		assertEquals(true, Player.tts("TEST"));
	}
	
	/**
	 * Test the option() method.
	 * It does this by ensuring the option() method returns true.
	 */
	@Test
	public void testOption() {
		assertEquals(true, Player.option("2,5,NULL,NULL,NULL"));
	}
	
	/**
	 * Test the display() method.
	 * It does this by ensuring the display() method returns true.
	 */
	@Test
	public void testDisplay() {
		assertEquals(true, Player.display("TEST"));
	}
	
	/**
	 * Test the tryParse() method.
	 * It does this by ensuring the tryParse() method returns the
	 * correct integer from a given string.
	 */
	@Test
	public void testTryParse() {
		String toParse = "10";
		assertEquals(10, (int) Player.tryParse(toParse));
		toParse = "11";
		assertEquals(11, (int) Player.tryParse(toParse));
		toParse = "12";
		assertEquals(12, (int) Player.tryParse(toParse));
		toParse = "9498573";
		assertEquals(9498573, (int) Player.tryParse(toParse));
	}
	
}
