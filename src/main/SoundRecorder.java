package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class SoundRecorder extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField soundFileName;
	
	private int recordingTimes = 0;
	private boolean recording = false;
	final SoundRecordingUtil recorder = new SoundRecordingUtil();
	private Thread recordThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                recorder.start();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }              
        }
    });

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SoundRecorder dialog = new SoundRecorder();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SoundRecorder() {
		setTitle("Sound Recorder");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SoundRecorder.class.getResource("/main/icon.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTextArea statusTextArea = new JTextArea();
		statusTextArea.setEditable(false);
		statusTextArea.setBounds(12, 112, 408, 93);
		
		statusTextArea.append("Initialized...");
		
		JLabel lblNameOfNew = new JLabel("Name of new sound file (exclude extension):");
		lblNameOfNew.setBounds(12, 13, 255, 16);
		contentPanel.add(lblNameOfNew);
		
		soundFileName = new JTextField();
		soundFileName.setBounds(271, 10, 149, 22);
		contentPanel.add(soundFileName);
		soundFileName.setColumns(10);
		
		JButton recordAction = new JButton("Start Recording");
		recordAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recording) {
					try {
						recorder.stop();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					statusTextArea.append("\n-- Recording Stopped --");
					recording = false;
					recordAction.setText("Start Recording");
				} else {
					if(soundFileName.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "Please enter a file name before recording!", "No File Name",
				                JOptionPane.ERROR_MESSAGE);		
					} else {
						statusTextArea.append("\n-- Recording Started --");
						recording = true;
						recordAction.setText("Stop Recording");
						recordingTimes++;
						recordThread.start();
					}
				}
			}
		});
		recordAction.setBounds(12, 45, 408, 25);
		contentPanel.add(recordAction);
		
		JLabel lblCurrentStatus = new JLabel("Current Status:");
		lblCurrentStatus.setBounds(12, 83, 149, 16);
		contentPanel.add(lblCurrentStatus);
		
		
		contentPanel.add(statusTextArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Done");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(recordingTimes > 0) {
							if(soundFileName.getText().length() == 0) {
								JOptionPane.showMessageDialog(null, "Please enter a file name before recording!", "No File Name",
						                JOptionPane.ERROR_MESSAGE);		
							} else {
								File wavFile = new File("C:/Users/Budware/Desktop/"+soundFileName.getText()+".wav");
								
								try {
									recorder.save(wavFile);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dispose();
							}
						} else {
							JOptionPane.showMessageDialog(null, "You did not record anything!", "No Recording Made",
					                JOptionPane.ERROR_MESSAGE);		
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
