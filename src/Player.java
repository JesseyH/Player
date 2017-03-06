

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * This class is included as an example of how to use the classes in the simulator package.
 * 
 */
@SuppressWarnings("unused")
public class Player {
	public static void main(String[] args) {
		int numberOfCells = 0, numberOfButtons = 0, buttonPressed = -1;
		String lastRead = null; 
		HashMap<String, String> cReadData = new HashMap<String, String>(); 
		HashMap<String, String> commandsTemp = new HashMap<String, String>(); 
		BufferedReader fileReader;
		Simulator sim = null;
		
		try {
			fileReader = new BufferedReader(new FileReader("./test.txt"));
			boolean askActive = false;
		    try {
				String line = fileReader.readLine();
				 while (line != null) {
					 Boolean result = Pattern.compile("::.*::").matcher(line).find();
					 if (result) {
						 String[] commands = line.split("	");
						 if (commands[0].contains("::ASK::") && !askActive) {
							 //Speak.textToSpeech(commands[1]);
						 } else if (commands[0].contains("::CELLS::") && !askActive) {
							 numberOfCells = Integer.parseInt(commands[1]);
						 } else if (commands[0].contains("::BUTTONS::") && !askActive) {
							 numberOfButtons = Integer.parseInt(commands[1]);
							sim = new Simulator(numberOfCells, numberOfButtons);
						 }  else if (commands[0].contains("::ASK::")) {
							 //Speak.textToSpeech(commands[1]);
						 } else if(commands[0].contains("::CREAD::") && !askActive) {
							 cReadData.put(commands[1], commands[2]);
							 askActive = true;
						 } else if(askActive) {
							 if (commands[0].contains("::COMMAND::")) {
								 String[] commandsB = fileReader.readLine().split("	");								 
								 for (int i=1; i<commands.length;i++) {
									 commandsTemp.put(commands[i], commandsB[i]);
								 }
							 }
						 }
					 } else{
						 if(line.length()>0) lastRead = line;
						 //Speak.textToSpeech(line);
						 System.out.println(line);
					 }
				     line = fileReader.readLine();
				    }
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error locating fie");		
		} finally {
			
		}	
		
		for (int i=0; i<numberOfButtons;i++) {
			int x = i;
			sim.getButton(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("PRESSED "+x);
				}
			});
		}		
	}
	private static int pressedOut(int i) {
		return i;
	}

}
