import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window.Type;

public class Editor {

	private JFrame frame;
	boolean configured = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PrintWriter writer = new PrintWriter("commands.txt");
			writer.close();
		} catch (FileNotFoundException e) {

		}
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
				ifTerminate();
			}
		});
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnNewButton = new JButton("Edit cofiguration");
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField nButtons = new JTextField(2);
				JTextField nCells = new JTextField(15);
				JPanel skioLineView = new JPanel();
				skioLineView.add(new JLabel("Number of buttons:"));
				skioLineView.add(nButtons);
				skioLineView.add(new JLabel("Number of braille cells:"));
				skioLineView.add(nCells);
				JOptionPane.showConfirmDialog(null, skioLineView, 
						"Skip to a line using button press", JOptionPane.OK_CANCEL_OPTION);
				if (nCells.getText().length()!=0 || nButtons.getText().length()!=0) {
					try {
						int braille = Integer.parseInt(nCells.getText()),
								button = Integer.parseInt(nButtons.getText());
						if (braille>0 && button>0) {
							try {
								BufferedReader readExistingLines = new BufferedReader(new FileReader("commands.txt"));
								String line, finalOut = "";
								while((line = readExistingLines.readLine())!=null) {
									if (!line.matches("Cell \\d+") && !line.matches("Button \\d+")) finalOut+=line+"\n";
								}
								FileWriter fileWriter = new FileWriter("commands.txt");
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
					JOptionPane.showMessageDialog(null,"Please enter number of braille cells and buttons");
				}
			}
		});
		frame.getContentPane().add(btnNewButton);


		JButton btnNewButton_6 = new JButton("Append Text");
		btnNewButton_6.setBackground(new Color(0, 0, 51));
		btnNewButton_6.setForeground(Color.WHITE);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = new JTextArea();
				JScrollPane scrollable = new JScrollPane(textArea);  
				textArea.setLineWrap(true);  
				textArea.setWrapStyleWord(true); 
				scrollable.setPreferredSize(new Dimension(400,200));
				JOptionPane.showMessageDialog(null, scrollable, "Repeatable texts",JOptionPane.YES_NO_OPTION);
				if(textArea.getText().length()>0) appendToFile(textArea.getText()+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_6);

		JButton btnNewButton_4 = new JButton("Add SFX");
		btnNewButton_4.setBackground(new Color(0, 0, 51));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser browseFile = new JFileChooser();
				browseFile.showOpenDialog(frame);
				File selectedFile = browseFile.getSelectedFile();
				appendToFile("\n/~sound:"+selectedFile+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_2 = new JButton("Pause");
		btnNewButton_2.setBackground(new Color(0, 0, 51));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pauseDuration = JOptionPane.showInputDialog(frame, "Duration of the pause:");
				appendToFile("/~pause:"+pauseDuration+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Set Voice");
		btnNewButton_3.setBackground(new Color(0, 0, 51));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String voice = JOptionPane.showInputDialog(frame, "Select a voice for TTS (1,2,3 or 4):");
				appendToFile("/~set-voice:"+voice+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_5 = new JButton("Display String");
		btnNewButton_5.setBackground(new Color(0, 0, 51));
		btnNewButton_5.setForeground(Color.WHITE);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strToDisp = JOptionPane.showInputDialog(frame, "Input string to display using braille cells:");
				appendToFile("/~disp-string:"+strToDisp+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_5);

		JButton btnNewButton_7 = new JButton("Add reapeatable text");
		btnNewButton_7.setBackground(new Color(0, 0, 51));
		btnNewButton_7.setForeground(Color.WHITE);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = new JTextArea();
				JScrollPane scrollable = new JScrollPane(textArea);  
				textArea.setLineWrap(true);  
				textArea.setWrapStyleWord(true); 
				scrollable.setPreferredSize(new Dimension(400,200));
				JOptionPane.showMessageDialog(null, scrollable, "Repeatable texts",JOptionPane.YES_NO_OPTION);
				appendToFile("/~repeat\n"+textArea.getText()+"\n/~endrepeat\n");
			}
		});
		frame.getContentPane().add(btnNewButton_7);

		JButton btnNewButton_1 = new JButton("Set repeat button");
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String repeatBtn = JOptionPane.showInputDialog(frame, "Please input index of the button that"
						+ "will handle repeat functionality");
				appendToFile("/~repeat-button:"+repeatBtn+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_8 = new JButton("Add target line for skip functionality");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String skipTarget = JOptionPane.showInputDialog(frame, "Line identifier:");
				appendToFile("/~"+skipTarget+"\n");
			}
		});
		btnNewButton_8.setBackground(new Color(0, 0, 51));
		btnNewButton_8.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_8);

		JButton btnNewButton_9 = new JButton("Skip to target line in file");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String targetLine = JOptionPane.showInputDialog(frame, "Target line identifier:");
				appendToFile("/~skip:"+targetLine+"\n");
			}
		});
		btnNewButton_9.setBackground(new Color(0, 0, 51));
		btnNewButton_9.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_9);

		JButton btnNewButton_10 = new JButton("Assign button to skip to line");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		btnNewButton_10.setBackground(new Color(0, 0, 51));
		btnNewButton_10.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_10);

		JButton btnNewButton_11 = new JButton("Request user input(button press)");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToFile("/~user-input\n");
			}
		});
		btnNewButton_11.setBackground(new Color(0, 0, 51));
		btnNewButton_11.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_11);

		JButton btnNewButton_12 = new JButton("Reset all the buttons");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToFile("/~reset-buttons\n");
			}
		});
		btnNewButton_12.setBackground(new Color(0, 0, 51));
		btnNewButton_12.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_12);

		JButton btnNewButton_13 = new JButton("Save and Exit");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ifTerminate();
			}
		});

		JButton btnNewButton_14 = new JButton("Clear all braille cells");
		btnNewButton_14.setBackground(new Color(0, 0, 51));
		btnNewButton_14.setForeground(Color.WHITE);
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToFile("/~disp-clearAll\n");
			}
		});
		frame.getContentPane().add(btnNewButton_14);

		JButton btnNewButton_15 = new JButton("Clear a single braille cell using ID");
		btnNewButton_15.setBackground(new Color(0, 0, 51));
		btnNewButton_15.setForeground(Color.WHITE);
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clearID = JOptionPane.showInputDialog(frame, "Clear braille cell with ID:");
				appendToFile("/~disp-clear-cell:"+clearID+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_15);

		JButton btnNewButton_16 = new JButton("Set braille cell state");
		btnNewButton_16.setBackground(new Color(0, 0, 51));
		btnNewButton_16.setForeground(Color.WHITE);
		btnNewButton_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		frame.getContentPane().add(btnNewButton_16);

		JButton btnSetBrailleCell = new JButton("Set braille cell to display a character");
		btnSetBrailleCell.setBackground(new Color(0, 0, 51));
		btnSetBrailleCell.setForeground(Color.WHITE);
		btnSetBrailleCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		frame.getContentPane().add(btnSetBrailleCell);

		JButton btnRaiseASingle = new JButton("Raise a single pin of single braille cell");
		btnRaiseASingle.setBackground(new Color(0, 0, 51));
		btnRaiseASingle.setForeground(Color.WHITE);
		btnRaiseASingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		frame.getContentPane().add(btnRaiseASingle);

		JButton btnLowerASingle = new JButton("Lower a single pin of single cell");
		btnLowerASingle.setBackground(new Color(0, 0, 51));
		btnLowerASingle.setForeground(Color.WHITE);
		btnLowerASingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		frame.getContentPane().add(btnLowerASingle);
		btnNewButton_13.setBackground(new Color(0, 128, 0));
		btnNewButton_13.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_13);
	}


	public static void appendToFile(String input) {
		FileWriter commandFile = null;
		try {
			commandFile = new FileWriter("commands.txt",true);
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

	public void ifTerminate() {
		if(configured) {
			frame.dispose();
		} else {
			int result = JOptionPane.showConfirmDialog(null, "Number of button and cells is not set, are you "
					+ "sure you want to exit?","Confirm",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION){
				frame.dispose();
			}
		}
	}

}
