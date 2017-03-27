import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
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
				appeandToFile("\nPLAY - "+selectedFile+"\n");
			}
		});
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_2 = new JButton("Command 4");
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Command 5");
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_5 = new JButton("Command 6");
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_7 = new JButton("Command 7");
		frame.getContentPane().add(btnNewButton_7);

		JButton btnNewButton_1 = new JButton("Command 8");
		frame.getContentPane().add(btnNewButton_1);
	}
	
    public static void appeandToFile(String input) {
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
