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

	@After
	public void tearDown() throws Exception {
		Scenario.reset();
	}

	/**
	 * Test the Scenario.initialize() method
	 */
	@Test
	public void testInitialize() {
		int brailleCells = 3;
		int buttonNumbers = 9;
		Scenario.initialize("test", System.getProperty("user.dir"), brailleCells, buttonNumbers);
		assertEquals("test", Scenario.getFileName());
		assertEquals(System.getProperty("user.dir"), Scenario.getDirectory());
		assertEquals(System.getProperty("user.dir"), Scenario.getDirectory());
		assertEquals(brailleCells, Scenario.getBrailleCells());
		assertEquals(buttonNumbers, Scenario.getButtons());
		fail("Not yet implemented");
	}

}
