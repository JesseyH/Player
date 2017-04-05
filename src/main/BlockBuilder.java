package main;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import main.core.BlockBuilderController;
import main.core.EditorPanelController;
import main.core.FolderBrowserListener;
import main.core.Scenario;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class BlockBuilder extends JDialog implements BlockBuilderController, FolderBrowserListener {
	private EditorPanelController editorPanel;
	private JTextField sectionName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox chckbxIsRepeatable;
	private JRadioButton skipToSection;
	private JRadioButton waitForInput;
	private JComboBox<Integer> buttonsDropdown;
	private JTextField skipToSectionName;
	private JTextField buttonAction;
	JTextArea buffer;
	
	/**
	 * Create the panel.
	 */
	public BlockBuilder(EditorPanelController editorPanel) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.editorPanel = editorPanel;
		setTitle("Scenario Section Builder");
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BlockBuilder.class.getResource("/main/icon.png")));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{43, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Current Section Name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		sectionName = new JTextField();
		GridBagConstraints gbc_sectionName = new GridBagConstraints();
		gbc_sectionName.fill = GridBagConstraints.HORIZONTAL;
		gbc_sectionName.gridx = 0;
		gbc_sectionName.gridy = 0;
		panel.add(sectionName, gbc_sectionName);
		sectionName.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Current Scenario Section:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel_2.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_5.add(scrollPane, gbc_scrollPane);
		
		buffer = new JTextArea();
		scrollPane.setViewportView(buffer);
		buffer.setEditable(false);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel_2.add(panel_4, gbc_panel_4);
		
		JButton btnWriteToBrai = new JButton("Write To Braille Cells");
		btnWriteToBrai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new WriteToBraille(BlockBuilder.this);
				dialog.setVisible(true);
			}
		});
		
		JLabel lblChooseVoice = new JLabel("Choose Voice");
		panel_4.add(lblChooseVoice);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"male 1", "female", "male 2", "male 3"}));
		panel_4.add(comboBox);
		panel_4.add(btnWriteToBrai);
		
		JButton btnNewButton = new JButton("Add Text To Be Spoken");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new WriteTTS(BlockBuilder.this);
				dialog.setVisible(true);
			}
		});
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Play Sound");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FolderBrowser(null, BlockBuilder.this, true);
			}
		});
		panel_4.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Record Sound ");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog soundRecorder = new SoundRecorder(BlockBuilder.this);
				soundRecorder.setVisible(true);
			}
		});
		panel_4.add(btnNewButton_2);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "What happens after completing this section?", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 1;
		panel_1.add(panel_11, gbc_panel_11);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[]{0, 0};
		gbl_panel_11.rowHeights = new int[]{0, 0};
		gbl_panel_11.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_11.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_11.setLayout(gbl_panel_11);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_11.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(UIManager.getBorder("RadioButton.border"));
		panel_3.add(panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] {0, 0, 0, 0};
		gbl_panel_7.rowHeights = new int[] {0, 0, 0, 0};
		gbl_panel_7.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		waitForInput = new JRadioButton("Wait For User Input");
		buttonGroup.add(waitForInput);
		waitForInput.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				
			}
			
		});
		GridBagConstraints gbc_waitForInput = new GridBagConstraints();
		gbc_waitForInput.insets = new Insets(0, 0, 5, 0);
		gbc_waitForInput.gridwidth = 3;
		gbc_waitForInput.gridx = 0;
		gbc_waitForInput.gridy = 0;
		panel_7.add(waitForInput, gbc_waitForInput);
		
		buttonsDropdown = new JComboBox<>();
		for(int i = 0; i < Scenario.getButtons(); i ++) {
			buttonsDropdown.addItem(i);
		}
		
		JLabel lblButton = new JLabel("Button #");
		GridBagConstraints gbc_lblButton = new GridBagConstraints();
		gbc_lblButton.insets = new Insets(0, 0, 5, 5);
		gbc_lblButton.gridx = 0;
		gbc_lblButton.gridy = 1;
		panel_7.add(lblButton, gbc_lblButton);
		
		JLabel lblButtonAction = new JLabel("Button Will Continue To Section:");
		GridBagConstraints gbc_lblButtonAction = new GridBagConstraints();
		gbc_lblButtonAction.insets = new Insets(0, 0, 5, 5);
		gbc_lblButtonAction.gridx = 1;
		gbc_lblButtonAction.gridy = 1;
		panel_7.add(lblButtonAction, gbc_lblButtonAction);
		GridBagConstraints gbc_buttonsDropdown = new GridBagConstraints();
		gbc_buttonsDropdown.insets = new Insets(0, 0, 0, 5);
		gbc_buttonsDropdown.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonsDropdown.gridx = 0;
		gbc_buttonsDropdown.gridy = 2;
		panel_7.add(buttonsDropdown, gbc_buttonsDropdown);
		
		buttonAction = new JTextField();
		GridBagConstraints gbc_buttonAction = new GridBagConstraints();
		gbc_buttonAction.insets = new Insets(0, 0, 0, 5);
		gbc_buttonAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonAction.gridx = 1;
		gbc_buttonAction.gridy = 2;
		panel_7.add(buttonAction, gbc_buttonAction);
		buttonAction.setColumns(10);
		
		JButton saveButtonAction = new JButton("Save Button Action");
		saveButtonAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scenario.bufferWriteSkipButtonAction((int) buttonsDropdown.getSelectedItem(), buttonAction.getText());
				refreshBuffer();
			}
		});
		GridBagConstraints gbc_saveButtonAction = new GridBagConstraints();
		gbc_saveButtonAction.gridx = 2;
		gbc_saveButtonAction.gridy = 2;
		panel_7.add(saveButtonAction, gbc_saveButtonAction);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(UIManager.getBorder("RadioButton.border"));
		panel_3.add(panel_6);
		
		skipToSection = new JRadioButton("Continue To Section (section name)");
		skipToSection.setSelected(true);
		skipToSection.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				switch(event.getStateChange()) {
				case 1: //When the checkbox is checked
					if(chckbxIsRepeatable.isSelected()) { //if this section will be repeated then we cannot allow this checkbox to be selected.
						showError("Because you have selected that this section is replayable, you must wait for user input\n"
								+ "so that they may select the continue button or repeat button!","Cannot Continue");
						waitForInput.setSelected(true);
					}
					break;
				}
			}
			
		});
		panel_6.add(skipToSection);
		buttonGroup.add(skipToSection);

		skipToSectionName = new JTextField();
		skipToSectionName.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(skipToSectionName);
		skipToSectionName.setColumns(10);
		
		chckbxIsRepeatable = new JCheckBox("Can this section be replayed?");
		chckbxIsRepeatable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(chckbxIsRepeatable.isSelected()) { //Should be like this because it appears that it becomes selected first, then action events are called
					waitForInput.setSelected(true);
					
					Scenario.clearBlockButtonBuffer();
					Scenario.bufferWriteReplayButtonAction(0);
					refreshBuffer();
					
					buttonsDropdown.removeAllItems();
					for(int i = 1; i < Scenario.getButtons(); i++) {
						buttonsDropdown.addItem(i);
					}
					JOptionPane.showMessageDialog(null, "Because you have chosen for this section to be replayable,\n"
														+ "button 0 has been automatically set to the 'replay' button.\n"
														+ "Any remaining buttons can be set to your liking.", "Auto Setup of Buttons",
			                JOptionPane.INFORMATION_MESSAGE);	
					
				} else {
					Scenario.clearBlockButtonBuffer();
					refreshBuffer();
					buttonsDropdown.removeAllItems();
					for(int i = 0; i < Scenario.getButtons(); i++) {
						buttonsDropdown.addItem(i);
					}
					JOptionPane.showMessageDialog(null, "All button actions have been reset!", "Buttons Reset",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		/*chckbxIsRepeatable.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				switch(event.getStateChange()) {
				case 1: //When the checkbox is checked
					waitForInput.setSelected(true);
					JOptionPane.showMessageDialog(null, "Because you have chosen for this section to be replayable, button 0 and button 1 have\n"
														+ "automatically been set to the 'replay' button and the 'continue' button respectively.\n"
														+ "Any remaining buttons can be set to your liking.", "Auto Setup of Buttons",
			                JOptionPane.INFORMATION_MESSAGE);	
					break;
				}
			}
			
		});*/
		panel_4.add(chckbxIsRepeatable);
		
		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 4;
		getContentPane().add(panel_9, gbc_panel_9);
		
		JButton btnFinish = new JButton("Finish Section");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishSection();
			}
		});
		panel_9.add(btnFinish);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_9.add(btnCancel);

	}
	
	public void finishSection() {
		ArrayList<String> buffer = Scenario.getBlockTextBuffer();
		if(buffer.isEmpty()) {
			showError("You have added no functionality to this section!", "Nothing Added");
		} else {
			if(sectionName.getText().length() == 0) {
				showError("Please enter a name for this section!", "Section Has No Name");
			}else if(skipToSection.isSelected()) { //If skip to section is selected
				if(skipToSectionName.getText().length() == 0) {
					showError("You have selected that you would like to continue to another section after completion of this section."
							+ "\nPlease enter the name of the section that you would like to continue to, even if the section does not yet exist!", "No Section To Continue To");
				} else {
					Scenario.bufferWriteSkip(skipToSectionName.getText());
					Scenario.bufferWriteSectionHeader(sectionName.getText());
					Scenario.copyBlockBuffersToScenarioBuffer();
					editorPanel.refreshBuffer();
					dispose();
				}
			//If wait for user input is selected
			} else if (waitForInput.isSelected()) {
				if(chckbxIsRepeatable.isSelected()) {
					if(Scenario.getBlockButtonBuffer().size() > 1) { //Make sure that atleast one other button other than the replay button has been set
						Scenario.bufferWriteReplay(); //First write the replay commands
						Scenario.bufferWriteSectionHeader(sectionName.getText());
						Scenario.copyBlockBuffersToScenarioBuffer();
						editorPanel.refreshBuffer();
						dispose();
					} else {
						showError("Because this secition is repeatable, you must assign atleast one button\n"
								+ "to handle continuing if the user does not decide to replay.", "More Button Actions Required");
					}
				} else {
					Scenario.bufferWriteSectionHeader(sectionName.getText());
					Scenario.copyBlockBuffersToScenarioBuffer();
					editorPanel.refreshBuffer();
					dispose();
				}
				
			} else {
				showError("Please enter a name for this section!", "Section Has No Name");
			}
		}
	}
	
	public void showError(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.ERROR_MESSAGE);		
	}
	
	@Override
	public void refreshBuffer() {
		buffer.setText("");
		String tempBuffer = "";
		for (int i = 0; i < Scenario.getBlockTextBuffer().size(); i++) {
			tempBuffer += Scenario.getBlockTextBuffer().get(i) + "\n";
		}
		for(String s: Scenario.getBlockButtonBuffer()) {
			if(s.length() != 0)
				tempBuffer += s + "\n";
		}
		buffer.setText(tempBuffer);
	}
	
	/**
	 * Called when the file chooser for audio files has been successfully closed 
	 * and thus an audio file has been selected.
	 * @param file The audio file that has been selected.
	 */
	@Override
	public void onSuccess(File file) {
		if(!Scenario.addAudioFile(file)) {
			showError("There was an error copying the audio file to the scenario directory. Please try again!", "Error With Audio File");	
		}
		Scenario.bufferWriteSound(file.getName());
		refreshBuffer();
	}
	@Override
	public void onFail() {
		
	}
}
