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

	private JFrame frame;
	boolean configured = false;
	static String currentFile = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
					window.frame.setVisible(true);
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

	public String createScenerio(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName+".txt");
			currentFile = fileName+".txt";
			writer.close();
		} catch (FileNotFoundException e) {

		}
		return null;
	}

	private void config() {
		BufferedReader readExistingLines;
		JTextField nButtons = new JTextField(2);
		JTextField nCells = new JTextField(15);
		JPanel skioLineView = new JPanel();
		skioLineView.add(new JLabel("Number of buttons:"));
		skioLineView.add(nButtons);
		skioLineView.add(new JLabel("Number of braille cells:"));
		skioLineView.add(nCells);
		int result = JOptionPane.showConfirmDialog(null, skioLineView, 
				"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
		if (nCells.getText().length()!=0 || nButtons.getText().length()!=0) {
			try {
				int braille = Integer.parseInt(nCells.getText()),
						button = Integer.parseInt(nButtons.getText());
				if (braille>0 && button>0) {
					try {
						readExistingLines = new BufferedReader(new FileReader(currentFile));
						String line, finalOut = "";
						while((line = readExistingLines.readLine())!=null) {
							if (!line.matches("Cell \\d+") && !line.matches("Button \\d+")) finalOut+=line+"\n";
						}
						FileWriter fileWriter = new FileWriter(currentFile);
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriter.write("Cell "+braille+"\n");
						bufferedWriter.write("Button "+button+"\n");
						bufferedWriter.write(finalOut);
						bufferedWriter.flush();
						bufferedWriter.close();
						configured = true;

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else JOptionPane.showMessageDialog(null,"Both inputs must be greater than 0");
			} catch (NumberFormatException x) {
				JOptionPane.showMessageDialog(null,"Both inputs must be integer");
			}
		} else {
			if (result == JOptionPane.OK_OPTION) 
				JOptionPane.showMessageDialog(null,"Please enter number of braille cells and buttons");
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Authoring");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		frame.setBounds(100, 100, 650, 350);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(ifTerminate()>=0) frame.dispose();
			}
		});
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnNewButton = new JButton("Edit cofiguration");
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(isScenerio()) {
					config();
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});

		JButton btnCreateNewScenerio = new JButton("Create new scenerio file");
		btnCreateNewScenerio.setBackground(Color.ORANGE);
		btnCreateNewScenerio.setForeground(Color.BLACK);
		btnCreateNewScenerio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentFile==null) {
					String fileName = JOptionPane.showInputDialog(frame, "Name of scenerio file:");
					if(fileName!=null && fileName.length()>0) {
						createScenerio(fileName);
						JOptionPane.showMessageDialog(null,"Scenerio file \""+currentFile+"\" is created");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Scenerio file alredy exists - "+currentFile);
				}
			}
		});
		frame.getContentPane().add(btnCreateNewScenerio);

		JButton btnEditExistingScenerio = new JButton("Edit existing scenerio file");
		btnEditExistingScenerio.setBackground(Color.ORANGE);
		btnEditExistingScenerio.setForeground(Color.BLACK);
		btnEditExistingScenerio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isScenerio()) {
					String file = browseFile();
					if(file!=null) {
						currentFile = file;
						JOptionPane.showMessageDialog(null, "Scenerio file '"+currentFile+"' is selected");
					} else {
						JOptionPane.showMessageDialog(null, "No file selected");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Scenerio file already exists");
				}
			}
		});
		frame.getContentPane().add(btnEditExistingScenerio);
		
		JButton btnClearScenerioFile = new JButton("Clear scenerio file");
		btnClearScenerioFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenerio()) {
					File file = new File(currentFile);
					file.delete();
					try {
						file.createNewFile();
						JOptionPane.showMessageDialog(null, "'"+file.getName()+"' cleared successfully!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnClearScenerioFile.setForeground(Color.BLACK);
		btnClearScenerioFile.setBackground(Color.ORANGE);
		frame.getContentPane().add(btnClearScenerioFile);
		frame.getContentPane().add(btnNewButton);


		JButton btnNewButton_6 = new JButton("Insert text into file");
		btnNewButton_6.setBackground(new Color(0, 0, 51));
		btnNewButton_6.setForeground(Color.WHITE);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					JTextArea textArea = new JTextArea();
					JScrollPane scrollable = new JScrollPane(textArea);  
					textArea.setLineWrap(true);  
					textArea.setWrapStyleWord(true); 
					scrollable.setPreferredSize(new Dimension(400,200));
					JOptionPane.showMessageDialog(null, scrollable, "Repeatable texts",JOptionPane.YES_NO_OPTION);
					if(textArea.getText().length()>0) appendToFile(textArea.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_6);

		JButton btnNewButton_4 = new JButton("Add sound");
		btnNewButton_4.setBackground(new Color(0, 0, 51));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					appendToFile("\n/~sound:"+browseFile()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_2 = new JButton("Add pause with duration");
		btnNewButton_2.setBackground(new Color(0, 0, 51));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					String pauseDuration = JOptionPane.showInputDialog(frame, "Duration of the pause:");
					appendToFile("/~pause:"+pauseDuration+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Set text-to-speech voice");
		btnNewButton_3.setBackground(new Color(0, 0, 51));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					String voice = JOptionPane.showInputDialog(frame, "Select a voice for TTS (1,2,3 or 4):");
					appendToFile("/~set-voice:"+voice+"\n");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_5 = new JButton("Display a message using braille cells");
		btnNewButton_5.setBackground(new Color(0, 0, 51));
		btnNewButton_5.setForeground(Color.WHITE);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					String strToDisp = JOptionPane.showInputDialog(frame, "Input string to display using braille cells:");
					appendToFile("/~disp-string:"+strToDisp+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_5);

		JButton btnNewButton_7 = new JButton("Add reapeatable text");
		btnNewButton_7.setBackground(new Color(0, 0, 51));
		btnNewButton_7.setForeground(Color.WHITE);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					JTextArea textArea = new JTextArea();
					JScrollPane scrollable = new JScrollPane(textArea);  
					textArea.setLineWrap(true);  
					textArea.setWrapStyleWord(true); 
					scrollable.setPreferredSize(new Dimension(400,200));
					JOptionPane.showMessageDialog(null, scrollable, "Repeatable texts",JOptionPane.YES_NO_OPTION);
					appendToFile("/~repeat\n"+textArea.getText()+"\n/~endrepeat\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_7);

		JButton btnNewButton_1 = new JButton("Assign repeat function to a button");
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					String repeatBtn = JOptionPane.showInputDialog(frame, "Please input index of the button that"
							+ "will handle repeat functionality");
					appendToFile("/~repeat-button:"+repeatBtn+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_8 = new JButton("Add target line for skip functionality");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenerio()) {
					String skipTarget = JOptionPane.showInputDialog(frame, "Line identifier:");
					appendToFile("/~"+skipTarget+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnNewButton_8.setBackground(new Color(0, 0, 51));
		btnNewButton_8.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_8);

		JButton btnNewButton_9 = new JButton("Skip to target line in file");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenerio()) {
					String targetLine = JOptionPane.showInputDialog(frame, "Target line identifier:");
					appendToFile("/~skip:"+targetLine+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnNewButton_9.setBackground(new Color(0, 0, 51));
		btnNewButton_9.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_9);

		JButton btnNewButton_10 = new JButton("Assign button to skip to line");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					JTextField buttonID = new JTextField(2);
					JTextField skipID = new JTextField(2);
					JPanel skioLineView = new JPanel();
					skioLineView.add(new JLabel("Button ID:"));
					skioLineView.add(buttonID);
					skioLineView.add(new JLabel("Skip to line with identifier:"));
					skioLineView.add(skipID);
					JOptionPane.showConfirmDialog(null, skioLineView, 
							"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
					appendToFile("/~skip-button:"+buttonID.getText()+" "+skipID.getText()+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnNewButton_10.setBackground(new Color(0, 0, 51));
		btnNewButton_10.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_10);

		JButton btnNewButton_11 = new JButton("Request user input(button press)");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					appendToFile("/~user-input\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnNewButton_11.setBackground(new Color(0, 0, 51));
		btnNewButton_11.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_11);

		JButton btnNewButton_12 = new JButton("Reset all the buttons");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					appendToFile("/~reset-buttons\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnNewButton_12.setBackground(new Color(0, 0, 51));
		btnNewButton_12.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_12);

		JButton btnNewButton_13 = new JButton("Save and Exit");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isScenerio()) {
					int returnCode = ifTerminate();
					if(returnCode==0 || returnCode==2) frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});

		JButton btnNewButton_14 = new JButton("Clear all braille cells");
		btnNewButton_14.setBackground(new Color(0, 0, 51));
		btnNewButton_14.setForeground(Color.WHITE);
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					appendToFile("/~disp-clearAll\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_14);

		JButton btnNewButton_15 = new JButton("Clear a single braille cell using ID");
		btnNewButton_15.setBackground(new Color(0, 0, 51));
		btnNewButton_15.setForeground(Color.WHITE);
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					String clearID = JOptionPane.showInputDialog(frame, "Clear braille cell with ID:");
					appendToFile("/~disp-clear-cell:"+clearID+"\n");
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_15);

		JButton btnNewButton_16 = new JButton("Set braille cell state");
		btnNewButton_16.setBackground(new Color(0, 0, 51));
		btnNewButton_16.setForeground(Color.WHITE);
		btnNewButton_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
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
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnNewButton_16);

		JButton btnSetBrailleCell = new JButton("Set braille cell to display a character");
		btnSetBrailleCell.setBackground(new Color(0, 0, 51));
		btnSetBrailleCell.setForeground(Color.WHITE);
		btnSetBrailleCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
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
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnSetBrailleCell);

		JButton btnRaiseASingle = new JButton("Raise a single pin of single braille cell");
		btnRaiseASingle.setBackground(new Color(0, 0, 51));
		btnRaiseASingle.setForeground(Color.WHITE);
		btnRaiseASingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
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
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnRaiseASingle);

		JButton btnLowerASingle = new JButton("Lower a single pin of single cell");
		btnLowerASingle.setBackground(new Color(0, 0, 51));
		btnLowerASingle.setForeground(Color.WHITE);
		btnLowerASingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
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
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		frame.getContentPane().add(btnLowerASingle);

		JButton btnSaveFile = new JButton("Save File");
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isScenerio()) {
					if(ifTerminate()>=0) {
						JOptionPane.showMessageDialog(null, "File '"+currentFile+"' saved successfully");
						currentFile = null;
					}
				} else {
					JOptionPane.showMessageDialog(null, "No scenerio file");
				}
			}
		});
		btnSaveFile.setBackground(new Color(0, 102, 51));
		btnSaveFile.setForeground(Color.WHITE);
		frame.getContentPane().add(btnSaveFile);
		btnNewButton_13.setBackground(new Color(0, 102, 51));
		btnNewButton_13.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_13);
	}

	public static boolean isScenerio() {
		return currentFile!=null;
	}

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

	public String browseFile() {
		JFileChooser browseFile = new JFileChooser();
		browseFile.showOpenDialog(frame);
		try {
			return browseFile.getSelectedFile().getPath();
		} catch(NullPointerException e) {
			return null;
		}
	}

	public int ifTerminate() {
		if(configured) {
			return 0;
		} else if(currentFile==null) {
			return 1;
		} else {
			if(JOptionPane.showConfirmDialog(null, "Number of button and cells is not set, are you "
					+ "sure you want to continue?","Confirm",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				return 2;
			}
		}
		return -1;
	}
}