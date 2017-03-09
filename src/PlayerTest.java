
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
		Player.start("input2.txt");				//Start the player app.
	}

	/**
	 * Tests the getCommand() method. It does this 
	 * by simulating commands that would typically be read in
	 * from the story file and testing the getCommand's return.
	 */
	@Test
	public void testGetCommand() {
		String sfxTest = "<SFX>,./resources/beep.wav";		//Simulate play sound affect command.
		String ttsTest = "<TTS>,HELLO";						//Simulate text to speech command.
		String optionTest = "<OPTION>,2,5,NULL,NULL,NULL";	//Simulate the create option command.
		String displayTest = "<DISPLAY>,test";				//Simulate the display text command.
		String noCommandTest = "<NOCOMMAND>,hello";			//simulate calling a command that doesn't exist.
		assertEquals(Player.getCommand(sfxTest), 0);		//Test <SFX> command.
		assertEquals(Player.getCommand(ttsTest), 1);		//Test <TTS> command.
		assertEquals(Player.getCommand(optionTest), 2);		//Test <OPTION> command.
		assertEquals(Player.getCommand(displayTest), 3);	//Test <DISPLAY> command.
		assertEquals(Player.getCommand(noCommandTest), 4);	//Test a command that doesnt exist (<NOCOMMAND>).
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
		assertEquals(Player.sfx("./resources/beep.wav"), true);
	}
	
	/**
	 * Test the tts() method.
	 * It does this by ensuring the tts() method returns true.
	 */
	@Test
	public void testTTS() {
		assertEquals(Player.tts("TEST"), true);
	}
	
	/**
	 * Test the option() method.
	 * It does this by ensuring the option() method returns true.
	 */
	@Test
	public void testOption() {
		assertEquals(Player.option("2,5,NULL,NULL,NULL"), true);
	}
	
	/**
	 * Test the display() method.
	 * It does this by ensuring the display() method returns true.
	 */
	@Test
	public void testDisplay() {
		assertEquals(Player.display("TEST"), true);
	}
	
	/**
	 * Test the tryParse() method.
	 * It does this by ensuring the tryParse() method returns the
	 * correct integer from a given string.
	 */
	@Test
	public void testTryParse() {
		String toParse = "10";
		assertEquals((int) Player.tryParse(toParse), 10);
		toParse = "11";
		assertEquals((int) Player.tryParse(toParse), 11);
		toParse = "12";
		assertEquals((int) Player.tryParse(toParse), 12);
		toParse = "9498573";
		assertEquals((int) Player.tryParse(toParse), 9498573);
	}
	
}
