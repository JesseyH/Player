package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.core.EditorViewController;
import main.core.FolderBrowserListener;
import main.core.Scenario;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Editor view is where the entire application begins. The editor view is the main JFrame that contains
 * the first JPanel which asks the user to enter braille cells, buttons, select a working directory and 
 * enter a scenario file name.
 * 
 * Upon the user clicking the "Start editing" button, the EditorView JFrame replaces the main content JPanel 
 * to the EditorPanel JPanel so that the user may begin editing the scenario file.
 * @author Group 6
 *
 */
@SuppressWarnings("serial")
public class EditorView extends JFrame implements EditorViewController, FolderBrowserListener {

	private JPanel contentPane;
	private JTextField scenarioFileName;
	private JTextField selectedDirectoryText;
	private JTextField brailleCellsText;
	private JTextField buttonsText;

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
		setBounds(100, 100, 655, 231);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel initialPanel = new JPanel();
		contentPane.add(initialPanel, "initialPanel");
		GridBagLayout gbl_initialPanel = new GridBagLayout();
		gbl_initialPanel.columnWidths = new int[]{0, 0, 0};
		gbl_initialPanel.rowHeights = new int[] {30, 30, 30, 30, 30, 0, 30};
		gbl_initialPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_initialPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_selectDirectoryLabel.anchor = GridBagConstraints.WEST;
		gbc_selectDirectoryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_selectDirectoryLabel.gridx = 0;
		gbc_selectDirectoryLabel.gridy = 1;
		initialPanel.add(selectDirectoryLabel, gbc_selectDirectoryLabel);
		
		JButton selectDirectoryBtn = new JButton("Select Directory");
		selectDirectoryBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FolderBrowser(EditorView.this, EditorView.this, false);
				
			}
			
		});
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
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if(getScenarioFileName().length() == 0) {
					showErrorMessage("Please enter the name of the new scenario file!");
				} else if (getScenarioFileDir().equals("No Directory Selected...") || getScenarioFileDir().equals("")) {
					showErrorMessage("Please select a directory to save the scenario file to!");
				} else {
					try {
						int brailleCells = Integer.parseInt(getBrailleCells());
						int buttons = Integer.parseInt(getButtons());
						if(buttons < 2) {
							showErrorMessage("A minimum of two buttons must be used!");
						} else {
							Scenario.initialize(getScenarioFileName(), getScenarioFileDir(), brailleCells, buttons);
							switchToEditorScreen();
						}
					} catch (NumberFormatException e) {
						showErrorMessage("Only enter numbers for the number of braille cells and buttons!");
					}
					
				}				
			}
			
		});
		
		JLabel lblEnterNumber = new JLabel("3) Enter number of braille cells:");
		lblEnterNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblEnterNumber = new GridBagConstraints();
		gbc_lblEnterNumber.anchor = GridBagConstraints.WEST;
		gbc_lblEnterNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterNumber.gridx = 0;
		gbc_lblEnterNumber.gridy = 3;
		initialPanel.add(lblEnterNumber, gbc_lblEnterNumber);
		
		brailleCellsText = new JTextField();
		GridBagConstraints gbc_brailleCellsText = new GridBagConstraints();
		gbc_brailleCellsText.insets = new Insets(0, 0, 5, 0);
		gbc_brailleCellsText.fill = GridBagConstraints.HORIZONTAL;
		gbc_brailleCellsText.gridx = 1;
		gbc_brailleCellsText.gridy = 3;
		initialPanel.add(brailleCellsText, gbc_brailleCellsText);
		brailleCellsText.setColumns(10);
		
		JLabel lblEnterNumber_1 = new JLabel("4) Enter number of buttons:");
		lblEnterNumber_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblEnterNumber_1 = new GridBagConstraints();
		gbc_lblEnterNumber_1.anchor = GridBagConstraints.WEST;
		gbc_lblEnterNumber_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterNumber_1.gridx = 0;
		gbc_lblEnterNumber_1.gridy = 4;
		initialPanel.add(lblEnterNumber_1, gbc_lblEnterNumber_1);
		
		buttonsText = new JTextField();
		GridBagConstraints gbc_buttonsText = new GridBagConstraints();
		gbc_buttonsText.insets = new Insets(0, 0, 5, 0);
		gbc_buttonsText.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonsText.gridx = 1;
		gbc_buttonsText.gridy = 4;
		initialPanel.add(buttonsText, gbc_buttonsText);
		buttonsText.setColumns(10);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 5;
		initialPanel.add(btnStart, gbc_btnStart);
		
		JPanel editorPanel = new EditorPanel();
		contentPane.add(editorPanel, "editorPanel");
		
	}

	/**
	 * Sets the "selected directory" text on the initialPanel JPanel.
	 */
	@Override
	public void setDirectoryText(String directory) {
		selectedDirectoryText.setText(directory);
	}
	
	/**
	 * Makes the EditorView switch to the MainEditor JPanel in which
	 * all scenario editing buttons are visible.
	 */
	@Override
	public void switchToEditorScreen() {
		setSize(1000, 500);
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
	 * Called when an JDialog containing an error message should be displayed.
	 * @param errorMessage The error message to display.
	 */
	@Override
	public void showErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Error",
                JOptionPane.ERROR_MESSAGE);		
	}

	/**
	 * Returns the text entered in the braille cells text area.
	 * @return Text inside the braille cells text area.
	 */
	@Override
	public String getBrailleCells() {
		return brailleCellsText.getText();
	}

	/**
	 * Returns the text entered in the button text area.
	 * @return Text inside the buttons text area.
	 */
	@Override
	public String getButtons() {
		return buttonsText.getText();
	}

	/**
	 * Method called when the user has selected a directory to write the 
	 * scenario files to using the FolderBrowser.
	 */
	@Override
	public void onSuccess(File file) {
		setDirectoryText(file.toString());
	}

	/**
	 * Method called when the user has closed the FolderBrowser.
	 */
	@Override
	public void onFail() {		
	}
}
