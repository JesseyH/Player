import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;

public class Editor {

	private JFrame Editor;
	static String currentFile = null;
	int btnAmt=0, cellAmt=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
					window.Editor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Editor() {
		initialize();
	}

	/**
	 * Creating scenario file
	 */
	public String createScenario(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName+".txt");
			currentFile = fileName+".txt";
			writer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unable to create scenario file");
		}
		return null;
	}

	/**
	 * Adding/Editing buttons and braille cells configuration at the beginning of the scenario file.
	 */
	private int[] config() {
		BufferedReader readExistingLines;
		JTextField nButtons = new JTextField(3);
		JTextField nCells = new JTextField(3);
		JPanel skioLineView = new JPanel();
		skioLineView.add(new JLabel("Number of buttons:"));
		skioLineView.add(nButtons);
		skioLineView.add(new JLabel("Number of braille cells:"));
		skioLineView.add(nCells);
		int result = JOptionPane.showConfirmDialog(null, skioLineView, 
				"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
		if (nCells.getText().length()!=0 || nButtons.getText().length()!=0) {
			try {
				int braille = Integer.parseInt(nCells.getText()), button = Integer.parseInt(nButtons.getText());
				btnAmt = button;
				cellAmt = braille;
				if (braille>0 && button>0) {
					try {
						readExistingLines = new BufferedReader(new FileReader(currentFile));
						String line, finalOut = "";
						while((line = readExistingLines.readLine())!=null) {
							if (!line.matches("Cells \\d+") && !line.matches("Button \\d+")) finalOut+=line+"\n";
						}
						FileWriter fileWriter = new FileWriter(currentFile);
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriter.write("Cells "+braille+"\n");
						bufferedWriter.write("Button "+button+"\n");
						bufferedWriter.write(finalOut);
						bufferedWriter.flush();
						bufferedWriter.close();
						return new int[] {braille,button};
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Both inputs must be greater than 0");
					return null;
				}
			} catch (NumberFormatException x) {
				JOptionPane.showMessageDialog(null,"Both inputs must be integer");
				return null;
			}
		} else {
			if (result == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(null,"Please enter number of braille cells and buttons");
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Editor = new JFrame("Authoring");
		Editor.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		Editor.setBounds(100, 100, 650, 350);
		Editor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Editor.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(ifTerminate()>=0) Editor.dispose();
			}
		});
		Editor.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		JButton editConfig = new JButton("Edit cofiguration");
		editConfig.setBackground(new Color(0, 0, 51));
		editConfig.setForeground(Color.WHITE);
		editConfig.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(isScenario()) {
					config();
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});

		JButton newScenario = new JButton("Create new scenario file");
		newScenario.setBackground(Color.ORANGE);
		newScenario.setForeground(Color.BLACK);
		newScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentFile==null) {
					String fileName = JOptionPane.showInputDialog(Editor, "Name of scenario file:");
					if(fileName!=null && fileName.length()>0) {
						createScenario(fileName);
						JOptionPane.showMessageDialog(null,"Scenario file \""+currentFile+"\" is created");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Scenario file alredy exists - "+currentFile);
				}
			}
		});
		Editor.getContentPane().add(newScenario);

		JButton editScenario = new JButton("Edit existing scenario file");
		editScenario.setBackground(Color.ORANGE);
		editScenario.setForeground(Color.BLACK);
		editScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isScenario()) {
					String file = browseFile();
					if(file!=null) {
						currentFile = file;
						JOptionPane.showMessageDialog(null, "Scenario file '"+currentFile+"' is selected");
					} else {
						JOptionPane.showMessageDialog(null, "No file selected");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Scenario file already exists");
				}
			}
		});
		Editor.getContentPane().add(editScenario);
		
		JButton clearScenario = new JButton("Clear scenario file");
		clearScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenario()) {
					File file = new File(currentFile);
					file.delete();
					try {
						file.createNewFile();
						JOptionPane.showMessageDialog(null, "'"+file.getName()+"' cleared successfully!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		clearScenario.setForeground(Color.BLACK);
		clearScenario.setBackground(Color.ORANGE);
		Editor.getContentPane().add(clearScenario);
		Editor.getContentPane().add(editConfig);


		JButton addText = new JButton("Insert text into file");
		addText.setBackground(new Color(0, 0, 51));
		addText.setForeground(Color.WHITE);
		addText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextArea textArea = new JTextArea();
					JScrollPane scrollable = new JScrollPane(textArea);  
					textArea.setLineWrap(true);  
					textArea.setWrapStyleWord(true); 
					scrollable.setPreferredSize(new Dimension(400,200));
					JOptionPane.showMessageDialog(null, scrollable, "Repeatable texts",JOptionPane.YES_NO_OPTION);
					if(textArea.getText().length()>0) appendToFile(textArea.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(addText);

		JButton addSound = new JButton("Add sound");
		addSound.setBackground(new Color(0, 0, 51));
		addSound.setForeground(Color.WHITE);
		addSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					appendToFile("\n/~sound:"+browseFile()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(addSound);

		JButton addPause = new JButton("Add pause with duration");
		addPause.setBackground(new Color(0, 0, 51));
		addPause.setForeground(Color.WHITE);
		addPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					String pauseDuration = JOptionPane.showInputDialog(Editor, "Duration of the pause:");
					appendToFile("/~pause:"+pauseDuration+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(addPause);

		JButton setVoice = new JButton("Set text-to-speech voice");
		setVoice.setBackground(new Color(0, 0, 51));
		setVoice.setForeground(Color.WHITE);
		setVoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					String voice = JOptionPane.showInputDialog(Editor, "Select a voice for TTS (1,2,3 or 4):");
					appendToFile("/~set-voice:"+voice+"\n");
				}
			}
		});
		Editor.getContentPane().add(setVoice);

		JButton dispMsg = new JButton("Display a message using braille cells");
		dispMsg.setBackground(new Color(0, 0, 51));
		dispMsg.setForeground(Color.WHITE);
		dispMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					String strToDisp = JOptionPane.showInputDialog(Editor, "Input string to display using braille cells:");
					appendToFile("/~disp-string:"+strToDisp+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(dispMsg);

		JButton repeatableText = new JButton("Add reapeatable text");
		repeatableText.setBackground(new Color(0, 0, 51));
		repeatableText.setForeground(Color.WHITE);
		repeatableText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextArea textArea = new JTextArea();
					JScrollPane scrollable = new JScrollPane(textArea);  
					textArea.setLineWrap(true);  
					textArea.setWrapStyleWord(true); 
					scrollable.setPreferredSize(new Dimension(400,200));
					JOptionPane.showMessageDialog(null, scrollable, "Repeatable texts",JOptionPane.YES_NO_OPTION);
					appendToFile("/~repeat\n"+textArea.getText()+"\n/~endrepeat\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(repeatableText);

		JButton repeatButton = new JButton("Assign repeat function to a button press");
		repeatButton.setBackground(new Color(0, 0, 51));
		repeatButton.setForeground(Color.WHITE);
		repeatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					String repeatBtn = JOptionPane.showInputDialog(Editor, "Index of the button that"
							+ " will handle repeat functionality:");
					appendToFile("/~repeat-button:"+repeatBtn+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(repeatButton);

		JButton skipLine = new JButton("Add target line for skip functionality");
		skipLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenario()) {
					String skipTarget = JOptionPane.showInputDialog(Editor, "Target line identifier:");
					appendToFile("/~"+skipTarget+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		skipLine.setBackground(new Color(0, 0, 51));
		skipLine.setForeground(Color.WHITE);
		Editor.getContentPane().add(skipLine);

		JButton skipToTarget = new JButton("Skip to target line in file");
		skipToTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenario()) {
					String targetLine = JOptionPane.showInputDialog(Editor, "Target line identifier:");
					appendToFile("/~skip:"+targetLine+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		skipToTarget.setBackground(new Color(0, 0, 51));
		skipToTarget.setForeground(Color.WHITE);
		Editor.getContentPane().add(skipToTarget);

		JButton skipButton = new JButton("Assign button to skip to line");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextField buttonID = new JTextField(2);
					JTextField skipID = new JTextField(10);
					JPanel skioLineView = new JPanel();
					skioLineView.add(new JLabel("Button ID:"));
					skioLineView.add(buttonID);
					skioLineView.add(new JLabel("Skip to line with identifier:"));
					skioLineView.add(skipID);
					JOptionPane.showConfirmDialog(null, skioLineView, 
							"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
					appendToFile("/~skip-button:"+buttonID.getText()+" "+skipID.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		skipButton.setBackground(new Color(0, 0, 51));
		skipButton.setForeground(Color.WHITE);
		Editor.getContentPane().add(skipButton);

		JButton getInput = new JButton("Request user input(button press)");
		getInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					appendToFile("/~user-input\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		getInput.setBackground(new Color(0, 0, 51));
		getInput.setForeground(Color.WHITE);
		Editor.getContentPane().add(getInput);

		JButton resetBtns = new JButton("Reset all the buttons");
		resetBtns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					appendToFile("/~reset-buttons\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		resetBtns.setBackground(new Color(0, 0, 51));
		resetBtns.setForeground(Color.WHITE);
		Editor.getContentPane().add(resetBtns);

		JButton saveAndExit = new JButton("Save and Exit");
		saveAndExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenario()) {
					int returnCode = ifTerminate();
					if(returnCode==0 || returnCode==2) Editor.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});

		JButton clrCells = new JButton("Clear all braille cells");
		clrCells.setBackground(new Color(0, 0, 51));
		clrCells.setForeground(Color.WHITE);
		clrCells.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					appendToFile("/~disp-clearAll\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(clrCells);

		JButton clrACell = new JButton("Clear a single braille cell using ID");
		clrACell.setBackground(new Color(0, 0, 51));
		clrACell.setForeground(Color.WHITE);
		clrACell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					String clearID = JOptionPane.showInputDialog(Editor, "Clear braille cell with ID:");
					appendToFile("/~disp-clear-cell:"+clearID+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(clrACell);

		JButton setACell = new JButton("Set braille cell state");
		setACell.setBackground(new Color(0, 0, 51));
		setACell.setForeground(Color.WHITE);
		setACell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextField cellID = new JTextField(2);
					JTextField state = new JTextField(15);
					JPanel setStateView = new JPanel();
					setStateView.add(new JLabel("Braille cell ID:"));
					setStateView.add(cellID);
					setStateView.add(new JLabel("Set to state(8-character sequence; i.e. 10101010, 1 - raised, 0 - low) :"));
					setStateView.add(state);
					JOptionPane.showConfirmDialog(null, setStateView, 
							"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
					appendToFile("/~disp-cell-pins:"+cellID.getText()+" "+state.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(setACell);

		JButton dispChar = new JButton("Set braille cell to display a character");
		dispChar.setBackground(new Color(0, 0, 51));
		dispChar.setForeground(Color.WHITE);
		dispChar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextField cellID = new JTextField(2);
					JTextField charToSet = new JTextField(2);
					JPanel setStateView = new JPanel();
					setStateView.add(new JLabel("Braille cell ID:"));
					setStateView.add(cellID);
					setStateView.add(new JLabel("Set braille to character:"));
					setStateView.add(charToSet);
					JOptionPane.showConfirmDialog(null, setStateView, 
							"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
					appendToFile("/~disp-cell-char:"+cellID.getText()+" "+charToSet.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(dispChar);

		JButton raisePin = new JButton("Raise a single pin of single braille cell");
		raisePin.setBackground(new Color(0, 0, 51));
		raisePin.setForeground(Color.WHITE);
		raisePin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextField cellID = new JTextField(2);
					JTextField pinID = new JTextField(2);
					JPanel setStateView = new JPanel();
					setStateView.add(new JLabel("Braille cell index:"));
					setStateView.add(cellID);
					setStateView.add(new JLabel("Index of pin to raise:"));
					setStateView.add(pinID);
					JOptionPane.showConfirmDialog(null, setStateView, 
							"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
					appendToFile("/~disp-cell-raise:"+cellID.getText()+" "+pinID.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(raisePin);

		JButton lowerPin = new JButton("Lower a single pin of single cell");
		lowerPin.setBackground(new Color(0, 0, 51));
		lowerPin.setForeground(Color.WHITE);
		lowerPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					JTextField cellID = new JTextField(2);
					JTextField pinID = new JTextField(2);
					JPanel setStateView = new JPanel();
					setStateView.add(new JLabel("Braille cell index:"));
					setStateView.add(cellID);
					setStateView.add(new JLabel("Index of pin to raise:"));
					setStateView.add(pinID);
					JOptionPane.showConfirmDialog(null, setStateView, 
							"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
					appendToFile("/~disp-cell-lower:"+cellID.getText()+" "+pinID.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		Editor.getContentPane().add(lowerPin);

		JButton saveFile = new JButton("Save and close file");
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenario()) {
					if(ifTerminate()>=0) {
						JOptionPane.showMessageDialog(null, "File '"+currentFile+"' saved successfully");
						currentFile = null;
					}
				} else {
					JOptionPane.showMessageDialog(null, "No scenario file");
				}
			}
		});
		saveFile.setBackground(new Color(0, 102, 51));
		saveFile.setForeground(Color.WHITE);
		Editor.getContentPane().add(saveFile);
		saveAndExit.setBackground(new Color(0, 102, 51));
		saveAndExit.setForeground(Color.WHITE);
		Editor.getContentPane().add(saveAndExit);
	}
	/**
	 * Checks to see if there is scenario file opened and ready to be edited
	 * @return True - if there exist scenario file; False - Otherwise
	 */
	public static boolean isScenario() {
		return currentFile!=null;
	}
	
	/**
	 * Append text to scenario file
	 * @param input - Text to append
	 */
	public static void appendToFile(String input) {
		FileWriter commandFile = null;
		try {
			commandFile = new FileWriter(currentFile,true);
			commandFile.write(input);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				commandFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Browse and open file
	 * @return path of the file selected
	 */
	public String browseFile() {
		JFileChooser browseFile = new JFileChooser();
		browseFile.showOpenDialog(Editor);
		try {
			return browseFile.getSelectedFile().getPath();
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Check to see if program should be or should not be closed based on user input and state of the program
	 * @return Close codes: 0 - OK to terminate without prompt; 1 - OK to close, but not save;
	 *  2 - Prompt user if it is OK to close the program and save file;
	 *  -1 - OK to terminate but not save
	 */
	public int ifTerminate() {
		if(isConfigured()) {
			return 0;
		} else if(currentFile==null) {
			return 1;
		} else {
			if(JOptionPane.showConfirmDialog(null, "Number of buttons and cells is not configured, are you "
					+ "sure you want to continue?","Confirm",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				return 2;
			}
		}
		return -1;
	}
	
	/**
	 * Check if input provided is integer
	 * @param testValue - value to test
	 * @return True - If testValue is integer; False - Otherwise
	 */
	public boolean isInt(String testValue) {
		try {
			getInt(testValue);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Check to see if testValue provided as input is single character
	 * @param testValue
	 * @return True - If testValue is single character; False - Otherwise
	 */
	public boolean isChar(String testValue) {
		return testValue.length()==1;
	}
	
	/**
	 * Check to see if testValue provided as input is correct cell value (i.e. it's within range of existing cells)
	 * @param testValue
	 * @return True - if testValue referring to cell exists; False - Otherwise
	 */
	public boolean isValidCell(String testValue) {
		return Integer.parseInt(testValue)<=cellAmt;
	}
	
	/**
	 * Check to see if testValue provided as input is correct button value (i.e. it's within range of existing buttons)
	 * @param testValue
	 * @return True - if testValue referring to button exists; False - Otherwise
	 */
	public boolean isValidBtn(String testValue) {
		return Integer.parseInt(testValue)<=btnAmt;
	}
	
	/**
	 * Converts string to integer
	 * @param value - String to be converted to integer
	 * @return integer value corresponding to string input "value"
	 */
	public int getInt(String value) {
		return Integer.parseInt(value);
	}
	
	/**
	 * Check to see if braille and button configuration are present in file
	 * @return True - configuration exists in file; False - otherwise
	 */
	@SuppressWarnings("resource")
	public boolean isConfigured() {
		try {
			BufferedReader readExistingLines = new BufferedReader(new FileReader(currentFile));
			if(readExistingLines.readLine().matches("Cells \\d+") 
					&& readExistingLines.readLine().matches("Button \\d+")) return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
}