import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class appendTextView {

	private JFrame frame;


	/**
	 * Create the application.
	 */
	public appendTextView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JTextArea textArea = new JTextArea(11,39);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textArea, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, 206, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, 424, SpringLayout.WEST, frame.getContentPane());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane textScroll = new JScrollPane(textArea);
		frame.getContentPane().add(textScroll);

		JButton btnAppendText = new JButton("Append Text");
		btnAppendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Editor.appendToFile(textArea.getText());
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnAppendText);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnAppendText, 0, SpringLayout.NORTH, btnCancel);
		springLayout.putConstraint(SpringLayout.EAST, btnAppendText, -14, SpringLayout.WEST, btnCancel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, 0, SpringLayout.EAST, textArea);
		frame.getContentPane().add(btnCancel);
	}
}
