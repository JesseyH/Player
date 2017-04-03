package main;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class mainWindow extends JPanel {

	/**
	 * Create the panel.
	 */
	public mainWindow() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		JPanel scrollalePanel = new JPanel();
		scrollalePanel.setPreferredSize(new Dimension(140,2000));
		JScrollPane scrollFrame = new JScrollPane(scrollalePanel);
		scrollalePanel.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension(155,450));
		add(scrollFrame);
		
		JLabel lblWarnings = new JLabel("Warnings");
		scrollFrame.setColumnHeaderView(lblWarnings);
		
		JTextArea textArea = new JTextArea();
		
		JButton btnNewButton = new JButton("Add block");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 109, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 156, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, 169, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -154, SpringLayout.EAST, this);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.append("Button clicked\n");				
				JButton btnNewButtonX = new JButton("X");
				scrollalePanel.add(btnNewButtonX);
				btnNewButtonX.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "TEST");					
					}
				});
				scrollalePanel.revalidate();
				
			}
		});
		add(btnNewButton);
		
		JScrollPane scrollable = new JScrollPane(textArea);
		springLayout.putConstraint(SpringLayout.NORTH, scrollable, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollable, -153, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollable, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollable, -10, SpringLayout.EAST, this);
		textArea.setLineWrap(true);  
		textArea.setWrapStyleWord(true); 
		scrollable.setPreferredSize(new Dimension(200, 200));
		add(scrollable);
		
		JLabel lblFile = new JLabel("FILE");
		scrollable.setColumnHeaderView(lblFile);

	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("TEST");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.getContentPane().add(new mainWindow());
		frame.setVisible(true);
	}
}
