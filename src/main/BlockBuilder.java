package main;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import main.core.Scenario;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;

public class BlockBuilder extends JDialog {
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField_1;
	private JTextField textField_3;
	private String bufferText;
	/**
	 * Create the panel.
	 */
	public BlockBuilder() {
		setTitle("Scenario Section Block Builder");
		setIconImage(Toolkit.getDefaultToolkit().getImage(BlockBuilder.class.getResource("/main/icon.png")));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{43, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Header", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
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
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dialog", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		
		JTextArea buffer = new JTextArea();
		scrollPane.setViewportView(buffer);
		buffer.setEditable(false);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel_2.add(panel_4, gbc_panel_4);
		
		JButton btnWriteToBrai = new JButton("Write to braille cell");
		btnWriteToBrai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new WriteToBraille();
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		});
		
		JLabel lblChooseVoice = new JLabel("Choose Voice");
		panel_4.add(lblChooseVoice);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"male 1", "female", "male 2", "male 3"}));
		panel_4.add(comboBox);
		panel_4.add(btnWriteToBrai);
		
		JButton btnNewButton = new JButton("Add TTS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new WriteTTS();
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		});
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add sound file");
		panel_4.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Record Sound");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog soundRecorder = new SoundRecorder();
				soundRecorder.setAlwaysOnTop(true);
				soundRecorder.setVisible(true);
			}
		});
		panel_4.add(btnNewButton_2);
		
		JCheckBox chckbxIsRepeatable = new JCheckBox("is repeatable");
		panel_4.add(chckbxIsRepeatable);
		
		JButton btnRefresh = new JButton("refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buffer.setText("");
				bufferText = "";
				for (int i = 0; i < Scenario.getBlockTextBuffer().size(); i++) {
					bufferText = bufferText + Scenario.getBlockTextBuffer().get(i) + "\n";
				}
				buffer.setText(bufferText);
			}
		});
		panel_4.add(btnRefresh);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Flow", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		gbl_panel_7.rowHeights = new int[]{0, 0, 0};
		gbl_panel_7.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Wait For User Input");
		buttonGroup.add(rdbtnNewRadioButton);
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton.gridwidth = 3;
		gbc_rdbtnNewRadioButton.gridx = 0;
		gbc_rdbtnNewRadioButton.gridy = 0;
		panel_7.add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);
		
		JComboBox<Integer> buttonsDropdown = new JComboBox<>();
		for(int i = 0; i < Scenario.getButtons(); i ++) {
			buttonsDropdown.addItem(i);
		}
		GridBagConstraints gbc_buttonsDropdown = new GridBagConstraints();
		gbc_buttonsDropdown.insets = new Insets(0, 0, 0, 5);
		gbc_buttonsDropdown.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonsDropdown.gridx = 0;
		gbc_buttonsDropdown.gridy = 1;
		panel_7.add(buttonsDropdown, gbc_buttonsDropdown);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 0, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 1;
		panel_7.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Save Button Action");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.gridx = 2;
		gbc_btnNewButton_3.gridy = 1;
		panel_7.add(btnNewButton_3, gbc_btnNewButton_3);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(UIManager.getBorder("RadioButton.border"));
		panel_3.add(panel_6);
		
		JRadioButton rdbtnSkiplocation = new JRadioButton("Continue To Section");
		rdbtnSkiplocation.setSelected(true);
		panel_6.add(rdbtnSkiplocation);
		buttonGroup.add(rdbtnSkiplocation);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 4;
		getContentPane().add(panel_9, gbc_panel_9);
		
		JButton btnFinish = new JButton("Finish Block");
		panel_9.add(btnFinish);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_9.add(btnCancel);

	}
}
