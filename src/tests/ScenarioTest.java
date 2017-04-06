package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.core.Scenario;

/**
 * JUnit Test for the Scenario class.
 * @author Group 6
 *
 */
public class ScenarioTest {
	
	private int brailleCells = 3;
	private int buttonNumbers = 9;
	private String fileName = "test";
	private String directory = System.getProperty("user.dir");
	
	@Before
	public void setup() throws Exception {
		Scenario.initialize(fileName, directory, brailleCells, buttonNumbers);
	}

	@After
	public void tearDown() throws Exception {
		Scenario.reset();
	}

	/**
	 * Test the Scenario.initialize() method.
	 */
	@Test
	public void testInitialize() {
		assertEquals(fileName, Scenario.getFileName());
		assertEquals(directory, Scenario.getDirectory());
		assertEquals(directory, Scenario.getDirectory());
		assertEquals(brailleCells, Scenario.getBrailleCells());
		assertEquals(buttonNumbers, Scenario.getButtons());
	}
	
	/**
	 * Test the Scenario.getFileName() method.
	 */
	@Test
	public void testGetFileName() {
		assertEquals(fileName, Scenario.getFileName());
	}
	
	/**
	 * Tests the Scenario.getDirectory() method.
	 */
	@Test
	public void testGetDirectory() {
		assertEquals(directory, Scenario.getDirectory());
	}
	
	/**
	 * Tests the Scenario.getBrailleCells() method.
	 */
	@Test
	public void testGetBrailleCells() {
		assertEquals(brailleCells, Scenario.getBrailleCells());
	}
	
	/**
	 * Tests the Scenario.getButtons() method.
	 */
	@Test
	public void testGetButtons() {
		assertEquals(buttonNumbers, Scenario.getButtons());
	}
	
	/**
	 * Tests the Scenario.getHeader() method.
	 */
	@Test
	public void testGetHeader() {
		String header = "testheader";
		Scenario.bufferWriteSectionHeader(header);
		assertEquals(header, Scenario.getHeader());
	}
	
	/**
	 * Tests the Scenario.bufferWriteBraille() method.
	 */
	@Test
	public void testBufferWriteBraille() {
		String text1 = "testbraille";
		String text2 = "tes";
		assertEquals(false, Scenario.bufferWriteBraille(text1));
		assertEquals(true, Scenario.bufferWriteBraille(text2));
		assertEquals("/~disp-string:" + text2, Scenario.getBlockTextBuffer().get(0));
	}
	
	/**
	 * Tests the Scenario.bufferWriteTTS() method.
	 */
	@Test
	public void testBufferWriteTTS() {
		String text = "what a scene!";
		assertEquals(true, Scenario.bufferWriteTTS(text));
		assertEquals(text, Scenario.getBlockTextBuffer().get(0));
	}
	
	/**
	 * Tests the Scenario.bufferWriteSound() method.
	 */
	@Test
	public void testBufferWriteSound() {
		String testFile = "test.wav";
		assertEquals(true, Scenario.bufferWriteSound(testFile));
		assertEquals("/~sound:"+testFile, Scenario.getBlockTextBuffer().get(0));
	}
	
	/**
	 * Tests the Scenario.bufferWriteSkip() method.
	 */
	@Test
	public void testBufferWriteSkip() {
		String section = "next";
		assertEquals(true, Scenario.bufferWriteSkip(section));
		assertEquals("/~skip:"+section, Scenario.getBlockTextBuffer().get(0));
	}
	
	/**
	 * Tests the Scenario.bufferWriteVoice() method.
	 */
	@Test
	public void testBufferWriteVoice() {
		int failVoice = 0;
		int voice = 1;
		assertEquals(false, Scenario.bufferWriteVoice(failVoice));
		assertEquals(true, Scenario.bufferWriteVoice(voice));
		assertEquals("/~set-voice:"+voice, Scenario.getBlockTextBuffer().get(0));
	}
	
	/**
	 * Tests the Scenario.bufferWriteReplay() method.
	 */
	@Test
	public void testBufferWriteReplay() {
		assertEquals(true, Scenario.bufferWriteReplay());
		assertEquals("/~repeat", Scenario.getBlockTextBuffer().get(0));
		assertEquals("/~endrepeat", Scenario.getBlockTextBuffer().get(1));
	}
	
	/**
	 * Tests the Scenario.bufferWriteSectionHeader() method.
	 */
	@Test
	public void testBufferWriteSectionHeader() {
		String header = "yep";
		assertEquals(true, Scenario.bufferWriteSectionHeader(header));
		assertEquals("/~"+header, Scenario.getBlockTextBuffer().get(0));
	}
	
	/**
	 * Test the Scenario.bufferWriteSkipButtonAction() method.
	 */
	@Test
	public void testBufferWriteSkipButtonAction() {
		int button = 0;
		String section = "boiiii";
		Scenario.getBlockButtonBuffer().add(button, "");	//Ensure that the index has been created before (simulating what would actually happen during program runtime).
		assertEquals(true, Scenario.bufferWriteSkipButtonAction(button, section));
		assertEquals("/~skip-button:"+ button + " " +section, Scenario.getBlockButtonBuffer().get(button));
	}
	
	/**
	 * Test the Scenario.bufferWriteReplayButtonAction() method.
	 */
	@Test
	public void testBufferWriteReplayButtonAction() {
		int button = 0;
		Scenario.getBlockButtonBuffer().add(button, "");	//Ensure that the index has been created before (simulating what would actually happen during program runtime).
		assertEquals(true, Scenario.bufferWriteReplayButtonAction(button));
		assertEquals("/~repeat-button:"+button, Scenario.getBlockButtonBuffer().get(button));
	}
	
	/**
	 * Test the Scenario.clearBlockTextBuffer() method.
	 */
	@Test
	public void testClearBlockTextBuffer() {
		assertEquals(true, Scenario.clearBlockTextBuffer());
		assertEquals(0, Scenario.getBlockTextBuffer().size());
	}
	
	/**
	 * Test the Scenario.clearBlockButtonBuffer() method.
	 */
	@Test
	public void testClearBlockButtonBuffer() {
		assertEquals(true, Scenario.clearBlockButtonBuffer());
		assertEquals(buttonNumbers, Scenario.getBlockButtonBuffer().size());
	}
	
	/**
	 * Test the Scenario.copyBlockBuffersToScenarioBuffer() method.
	 */
	@Test
	public void testCopyBlockBuffersToScenarioBuffer() {
		String text = "whats going on man?";
		int button = 0;
		String section = "next";
		assertEquals(true, Scenario.bufferWriteTTS(text));
		Scenario.getBlockButtonBuffer().add(button, "");	//Ensure that the index has been created before (simulating what would actually happen during program runtime).
		assertEquals(true, Scenario.bufferWriteSkipButtonAction(button, section));
		assertEquals(true, Scenario.copyBlockBuffersToScenarioBuffer());
		assertEquals(text, Scenario.getScenarioBuffer().get(0));
		assertEquals("/~reset-buttons", Scenario.getScenarioBuffer().get(1));
		assertEquals("/~skip-button:"+ button + " " +section, Scenario.getScenarioBuffer().get(2));
		assertEquals("/~user-input", Scenario.getScenarioBuffer().get(3));
	}
	
	/**
	 * Tests the Scenario.getScenarioBuffer() method.
	 */
	@Test
	public void testGetScenarioBuffer() {
		Scenario.reset();
		assertEquals(null, Scenario.getScenarioBuffer());
	}
	
	/**
	 * Tests the Scenario.getBlockTextBuffer() method.
	 */
	@Test
	public void testGetBlockTextBuffer() {
		Scenario.reset();
		assertEquals(null, Scenario.getBlockTextBuffer());
	}
	/**
	 * Tests the Scenario.getBlockButtonBuffer() method.
	 */
	@Test
	public void testGetBlockButtonBuffer() {
		Scenario.reset();
		assertEquals(null, Scenario.getBlockButtonBuffer());
	}
	
	/**
	 * Test the Scenario.getMissingSections() method.
	 */
	@Test
	public void testGetMissingSections() {
		Scenario.reset();
		assertEquals(null, Scenario.getMissingSections());
	}
	
	/**
	 * Test the Scenario.isInMissing() method.
	 */
	@Test
	public void testIsInMissing() {
		String test[] = {"test"};
		Scenario.getMissingSections().add(test);
		assertEquals(true, Scenario.isInMissing(test[0]));
	}
  }
