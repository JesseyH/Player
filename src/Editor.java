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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton = new JButton("Cofiguration");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new configurationView();
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		
		JButton btnNewButton_6 = new JButton("Append Text");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new appendTextView();
			}
		});
		frame.getContentPane().add(btnNewButton_6);
		
		JButton btnNewButton_4 = new JButton("Add SFX");
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
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pauseDuration = JOptionPane.showInputDialog(frame, "Duration of the pause:");
				appendToFile("/~pause:"+pauseDuration+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Set Voice");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String voice = JOptionPane.showInputDialog(frame, "Select a voice for TTS (1,2,3 or 4):");
				appendToFile("/~set-voice:"+voice+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_5 = new JButton("Display String");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strToDisp = JOptionPane.showInputDialog(frame, "Input string to display using braille cells:");
				appendToFile("/~disp-string:"+strToDisp+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_7 = new JButton("Input reapeatable text");
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
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String repeatBtn = JOptionPane.showInputDialog(frame, "Please input index of the button that"
						+ "will handle repeat functionality");
				appendToFile("/~repeat-button:"+repeatBtn+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_1);
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
