package main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import main.core.EditorPanelController;
import main.core.Scenario;

import javax.swing.UIManager;
import java.awt.Color;

/**
 * EditorPanel interface is the JPanel that contains the "To Do List" JPanel,
 * the current scenario file buffer JPanel, and the buttons to add a new section as well
 * as save the scenario file.
 * @author Group 6
 *
 */
@SuppressWarnings("serial")
public class EditorPanel extends JPanel implements EditorPanelController {
	
	private JTextArea scenarioBufferText;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public EditorPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {500, 500, 0};
		gridBagLayout.rowHeights = new int[] {400, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "To Do List", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Scenario File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		scenarioBufferText = new JTextArea();
		scenarioBufferText.setEditable(false);
		scrollPane_1.setViewportView(scenarioBufferText);
		
		JButton addNewSection = new JButton("Add New Section");
		addNewSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scenario.clearBlockTextBuffer();
				Scenario.clearBlockButtonBuffer();
				JDialog blockBuilder = new BlockBuilder(EditorPanel.this);
				blockBuilder.setSize(1000, 500);
				blockBuilder.setVisible(true);
			}
		});
		GridBagConstraints gbc_addNewSection = new GridBagConstraints();
		gbc_addNewSection.insets = new Insets(0, 0, 0, 5);
		gbc_addNewSection.anchor = GridBagConstraints.EAST;
		gbc_addNewSection.gridx = 0;
		gbc_addNewSection.gridy = 1;
		add(addNewSection, gbc_addNewSection);
		
		JButton saveScenario = new JButton("Save Scenario");
		saveScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Scenario.saveScenarioToFile()) {
					JOptionPane.showMessageDialog(null, "Scenario has been saved to the text file!", "Scenario Saved",
			                JOptionPane.INFORMATION_MESSAGE);	
				} else {
					JOptionPane.showMessageDialog(null, "There is no scenario to save!", "No Scenario",
			                JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		GridBagConstraints gbc_saveScenario = new GridBagConstraints();
		gbc_saveScenario.anchor = GridBagConstraints.WEST;
		gbc_saveScenario.gridx = 1;
		gbc_saveScenario.gridy = 1;
		add(saveScenario, gbc_saveScenario);

	}

	/**
	 * Refresh the text area of the main editor panel that shows the overall
	 * scenario buffer text.
	 */
	@Override
	public void refreshBuffer() {
		ArrayList<String> buffer = Scenario.getScenarioBuffer();
		String temp = "";
		for (String s : buffer) {
			temp += s + "\n";
		}
		scenarioBufferText.setText(temp);
	}

}
