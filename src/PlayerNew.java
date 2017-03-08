import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**This class reads a Story file and displays it to a Braille Simulator
 * <p>
 * The main method 
 * @author Group 6
 *
 */
public class PlayerNew {
	private static Simulator simulator = new Simulator(5,5);
	protected static ArrayList<String> lines;
	private static int buttons = 5, cells = 5;
	protected static int index = 0;
	private static OptionHandler l = new OptionHandler();
	
	public static void main(String args[]){
		getCommand("<OPTION>,1,2,3,NULL,NULL");
	}
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static void getCommand(String s){
		String split[] = new String[2];
		split = s.split(",", 2);
		if (split[0].equals("<SFX>")) {
			sfx(split[1]);
			System.out.println("SFX");
		} else if (split[0].equals("<TTS>")) {
			tts(split[1]);
			System.out.println("TTS");
		} else if (split[0].equals("<OPTION>")) {
			option(split[1]);
			System.out.println("OPTION " + split[1]);
		} else if (split[0].equals("<DISPLAY>")) {
			display(s);
			System.out.println("DISPLAY");
		} else {
			new Exception("COMMAND WAS NOT FOUND!");
		}
	}
	
	public static void next() {
		getCommand(lines.get(index + 1));
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public static void sfx(String fileName) {
		Speak.playSound(fileName);
		next();
	}
	
	/**
	 * 
	 * @param text
	 */
	public static void tts(String text) {
		Speak.textToSpeech(text);
		next();
	}
	
	/**
	 * 
	 * @param s
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
	 * 
	 * @param aString
	 */
	public static void display(String aString) {
		simulator.displayString(aString);
		next();
	}
}

/**
 * 
 * @author Group 6
 *
 */
class OptionHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		System.out.println(s);
		if (s != "NULL") {
			PlayerNew.index = Integer.valueOf(s);
			PlayerNew.getCommand(PlayerNew.lines.get(PlayerNew.index));
		} else {
			PlayerNew.tts("Not a Valid Option. Please Try again.");
		}
	}

}