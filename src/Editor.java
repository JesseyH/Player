import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window.Type;

public class Editor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter("commands.txt");
            writer.write("Braille - 5\n");
            writer.write("Buttons - 5\n");
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\IM\\Desktop\\IM\\workspace\\Authoring\\res\\york-icon - Copy.png"));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton = new JButton("Cofiguration");
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new configurationView();
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		
		JButton btnNewButton_6 = new JButton("Append Text");
		btnNewButton_6.setBackground(new Color(0, 0, 51));
		btnNewButton_6.setForeground(Color.WHITE);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new appendTextView();
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
		
		JButton btnNewButton_8 = new JButton("Command 9");
		btnNewButton_8.setBackground(new Color(0, 0, 51));
		btnNewButton_8.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("Command 10");
		btnNewButton_9.setBackground(new Color(0, 0, 51));
		btnNewButton_9.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("Command 11");
		btnNewButton_10.setBackground(new Color(0, 0, 51));
		btnNewButton_10.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("Command 12");
		btnNewButton_11.setBackground(new Color(0, 0, 51));
		btnNewButton_11.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("Command 13");
		btnNewButton_12.setBackground(new Color(0, 0, 51));
		btnNewButton_12.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("Save and Exit");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnNewButton_13.setBackground(new Color(0, 102, 0));
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

}
