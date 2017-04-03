package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.actionhandlers.CreateNew;
import main.actionhandlers.EditExisting;
import main.actionhandlers.StartEditing;
import main.core.EditorViewController;

import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;

public class EditorView extends JFrame implements EditorViewController {

	private JPanel contentPane;
	private JTextField scenarioFileName;
	private JTextField selectedDirectoryText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorView frame = new EditorView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditorView() {
		setResizable(false);
		setTitle("Group 6 Authoring App");
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditorView.class.getResource("/main/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel initialPanel = new JPanel();
		contentPane.add(initialPanel, "initialPanel");
		GridBagLayout gbl_initialPanel = new GridBagLayout();
		gbl_initialPanel.columnWidths = new int[]{0, 0, 0};
		gbl_initialPanel.rowHeights = new int[] {30, 30, 30, 30, 30, 30};
		gbl_initialPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_initialPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		initialPanel.setLayout(gbl_initialPanel);
		
		JLabel scenarioFileNameLabel = new JLabel("1) Name of the new scenario file (exclude file extension):");
		scenarioFileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_scenarioFileNameLabel = new GridBagConstraints();
		gbc_scenarioFileNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_scenarioFileNameLabel.anchor = GridBagConstraints.WEST;
		gbc_scenarioFileNameLabel.gridx = 0;
		gbc_scenarioFileNameLabel.gridy = 0;
		initialPanel.add(scenarioFileNameLabel, gbc_scenarioFileNameLabel);
		
		scenarioFileName = new JTextField();
		scenarioFileName.setText("new_scenario");
		scenarioFileName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_scenarioFileName = new GridBagConstraints();
		gbc_scenarioFileName.insets = new Insets(0, 0, 5, 0);
		gbc_scenarioFileName.fill = GridBagConstraints.HORIZONTAL;
		gbc_scenarioFileName.gridx = 1;
		gbc_scenarioFileName.gridy = 0;
		initialPanel.add(scenarioFileName, gbc_scenarioFileName);
		scenarioFileName.setColumns(10);
		
		JLabel selectDirectoryLabel = new JLabel("2) Select the directory to save the scenario file to:");
		selectDirectoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_selectDirectoryLabel = new GridBagConstraints();
		gbc_selectDirectoryLabel.anchor = GridBagConstraints.EAST;
		gbc_selectDirectoryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_selectDirectoryLabel.gridx = 0;
		gbc_selectDirectoryLabel.gridy = 1;
		initialPanel.add(selectDirectoryLabel, gbc_selectDirectoryLabel);
		
		JButton selectDirectoryBtn = new JButton("Select Directory");
		selectDirectoryBtn.addActionListener(new CreateNew(this));
		GridBagConstraints gbc_selectDirectoryBtn = new GridBagConstraints();
		gbc_selectDirectoryBtn.fill = GridBagConstraints.BOTH;
		gbc_selectDirectoryBtn.insets = new Insets(0, 0, 5, 0);
		gbc_selectDirectoryBtn.gridx = 1;
		gbc_selectDirectoryBtn.gridy = 1;
		initialPanel.add(selectDirectoryBtn, gbc_selectDirectoryBtn);
		
		JLabel selectedDirectoryLabel = new JLabel("Selected Directory:");
		selectedDirectoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_selectedDirectoryLabel = new GridBagConstraints();
		gbc_selectedDirectoryLabel.anchor = GridBagConstraints.EAST;
		gbc_selectedDirectoryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_selectedDirectoryLabel.gridx = 0;
		gbc_selectedDirectoryLabel.gridy = 2;
		initialPanel.add(selectedDirectoryLabel, gbc_selectedDirectoryLabel);
		
		selectedDirectoryText = new JTextField();
		selectedDirectoryText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		selectedDirectoryText.setEditable(false);
		selectedDirectoryText.setText("No Directory Selected...");
		GridBagConstraints gbc_selectedDirectoryText = new GridBagConstraints();
		gbc_selectedDirectoryText.insets = new Insets(0, 0, 5, 0);
		gbc_selectedDirectoryText.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectedDirectoryText.gridx = 1;
		gbc_selectedDirectoryText.gridy = 2;
		initialPanel.add(selectedDirectoryText, gbc_selectedDirectoryText);
		selectedDirectoryText.setColumns(10);
		
		JButton btnStart = new JButton("Start Editing");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStart.addActionListener(new StartEditing(this));
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.insets = new Insets(0, 0, 0, 5);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 4;
		initialPanel.add(btnStart, gbc_btnStart);
		
		JPanel editorPanel = new JPanel();
		contentPane.add(editorPanel, "editorPanel");
		SpringLayout sl_editorPanel = new SpringLayout();
		editorPanel.setLayout(sl_editorPanel);
		
		JButton btnAddBlock = new JButton("Add Question");
		btnAddBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		sl_editorPanel.putConstraint(SpringLayout.NORTH, btnAddBlock, 10, SpringLayout.NORTH, editorPanel);
		sl_editorPanel.putConstraint(SpringLayout.WEST, btnAddBlock, 10, SpringLayout.WEST, editorPanel);
		editorPanel.add(btnAddBlock);
		
	}

	/**
	 * Sets the "selected directory" text on the initialPanel JPanel.
	 */
	@Override
	public void setDirectoryText(String directory) {
		selectedDirectoryText.setText(directory);
	}
	
	@Override
	public void switchToEditorScreen() {
		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		cardLayout.show(contentPane, "editorPanel");
	}
	
	/**
	 * Returns the scenario file name entered into the editor screen.
	 * @return The scenario file name.
	 */
	@Override
	public String getScenarioFileName() {
		return scenarioFileName.getText();
	}

	/**
	 * Returns the directory in which the scenario file should be saved into.
	 * @return The directory to save the scenario file into.
	 */
	@Override
	public String getScenarioFileDir() {
		return selectedDirectoryText.getText();
	}

	/**
	 * Shows a popup containing an error message.
	 * @param errorMessage The error message to display
	 */
	@Override
	public void showErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Error",
                JOptionPane.ERROR_MESSAGE);		
	}
}
