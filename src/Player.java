import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * This class is included as an example of how to use the classes in the simulator package.
 * 
 */
public class Player {
	static String lastRead;

	public static void main(String[] args) throws InterruptedException {
		BufferedReader fileReader = null;
		Simulator simulator = null;
		String line;
		String[] subStrings, subStringsB;

		try {
			fileReader = new BufferedReader(new FileReader("./test.txt"));
			try {
				while((line = fileReader.readLine())!=null) {
					if(Pattern.compile("<<.*>>").matcher(line).find()) {
						subStrings = line.split("	");
						if(subStrings[0].contains("<<COMMAND>>")) {
							for (int i=1; i<subStrings.length;i++) {
								subStringsB = subStrings[i].split("=");
								int buttonID = Integer.parseInt(subStringsB[0].replaceAll("[^0-9]", "")) - 1;
								initActionListner(buttonID, subStringsB[1], simulator, (subStringsB.length>2) ? subStringsB[2]:null);
							}
						} else if(subStrings[0].contains("<<CELLS>>")) {
							simulator = new Simulator(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[3]));
						}
					} else {
						readText(line);
					}
					lastRead=line;
				}
			} catch (IOException e) {
				System.err.println("Error occured while reading the file");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error location the file");
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.err.println("Error closing file");
			}
		}
	}
	
	public static void initActionListner(int buttonID, String command, Simulator simulator, String componentA) {
		System.out.println(buttonID);
		simulator.getButton(buttonID).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				switch(command) {
				case "PLAY": playSound(componentA);
				break;
				case "CONTINUE": ;
				break;
				case "REPEAT": {
					readText(Player.lastRead);
					System.out.println(Player.lastRead);
				}
				break;
				case "REPEATB": readText(componentA);
				break;
				case "REPEATC": repeatSub(Player.lastRead);
				break;
				}
			}
		});
	}

	public static void readText(String textToRead) {
	//	Speak.textToSpeech(textToRead);
		System.out.println(textToRead);
	}

	public static void playSound(String filepath) {
		Speak.playSound(filepath);
	}

	public static void repeatLast(String textToRepeat) {
		//Speak.textToSpeech(textToRepeat);
		System.out.println(textToRepeat);
	}

	public static void repeatSub(String textToRepeat) {
		String textToRepeatTemp = textToRepeat;
		while(textToRepeatTemp.indexOf("<")>0) {
			String repeat = textToRepeatTemp.substring(textToRepeatTemp.indexOf("<") + 1, textToRepeatTemp.indexOf(">"));
			//Speak.textToSpeech(repeat);
			System.out.println(repeat);
			textToRepeatTemp = textToRepeatTemp.substring(textToRepeatTemp.indexOf(">") + 1, textToRepeatTemp.length());		
		}
	}
}
