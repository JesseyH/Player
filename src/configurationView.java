import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class configurationView {

	private JFrame frame;
	private JTextField brailles;
	private JTextField buttons;
	private JLabel lblNumberOfButtons;
	private final Action action = new SwingAction();


	/**
	 * Create the application.
	 */
	public configurationView() {
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
		frame = new JFrame("Braille Configuration");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		brailles = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, brailles, 64, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, brailles, 39, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, brailles, 102, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, brailles, 361, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(brailles);
		brailles.setColumns(10);
		
		buttons = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, buttons, 45, SpringLayout.SOUTH, brailles);
		springLayout.putConstraint(SpringLayout.WEST, buttons, 0, SpringLayout.WEST, brailles);
		springLayout.putConstraint(SpringLayout.SOUTH, buttons, 83, SpringLayout.SOUTH, brailles);
		springLayout.putConstraint(SpringLayout.EAST, buttons, 0, SpringLayout.EAST, brailles);
		frame.getContentPane().add(buttons);
		buttons.setColumns(10);
		
		JLabel lblNumberOfBraille = new JLabel("Number of braille cells");
		springLayout.putConstraint(SpringLayout.WEST, lblNumberOfBraille, 0, SpringLayout.WEST, brailles);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNumberOfBraille, -6, SpringLayout.NORTH, brailles);
		frame.getContentPane().add(lblNumberOfBraille);
		
		lblNumberOfButtons = new JLabel("Number of buttons");
		springLayout.putConstraint(SpringLayout.WEST, lblNumberOfButtons, 0, SpringLayout.WEST, brailles);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNumberOfButtons, -6, SpringLayout.NORTH, buttons);
		frame.getContentPane().add(lblNumberOfButtons);
		
		JButton setConfig = new JButton("Confirm");
		setConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (brailles.getText().length()!=0 || buttons.getText().length()!=0) {
					try {
						int braille = Integer.parseInt(brailles.getText()),
								button = Integer.parseInt(buttons.getText());
						if (braille>0 && button>0) {
                            try {
                                RandomAccessFile editFile = new RandomAccessFile("commands.txt", "rw");
                                editFile.seek(0);
                                editFile.write(("Cells "+braille+"\n").getBytes());
                                editFile.write(("Button "+button+"\n").getBytes());
                                editFile.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
							frame.dispose();
						} else JOptionPane.showMessageDialog(null,"Both inputs must be greater than 0");
					} catch (NumberFormatException x) {
						JOptionPane.showMessageDialog(null,"Both inputs must be integer");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Please enter number of braille cells and buttons");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, setConfig, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, setConfig, -108, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(setConfig);
		
		JButton cancelConfig = new JButton("Cancel");
		cancelConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, cancelConfig, 0, SpringLayout.NORTH, setConfig);
		springLayout.putConstraint(SpringLayout.WEST, cancelConfig, 6, SpringLayout.EAST, setConfig);
		frame.getContentPane().add(cancelConfig);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
