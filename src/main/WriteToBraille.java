package main;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import main.core.BlockBuilderController;
import main.core.Scenario;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class WriteToBraille extends JDialog {
	
	private JTextField brailleText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WriteToBraille dialog = new WriteToBraille(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WriteToBraille(BlockBuilderController blockBuilder) {
		setTitle("Write To Braille Cells");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WriteToBraille.class.getResource("/main/icon.png")));
		setBounds(100, 100, 306, 146);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{432, 0};
		gridBagLayout.rowHeights = new int[] {40, 35, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input the text to be shown accross braille cells", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			getContentPane().add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] {50};
			gbl_panel.rowHeights = new int[] {30, 0};
			gbl_panel.columnWeights = new double[]{1.0};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				brailleText = new JTextField();
				GridBagConstraints gbc_brailleText = new GridBagConstraints();
				gbc_brailleText.fill = GridBagConstraints.HORIZONTAL;
				gbc_brailleText.gridx = 0;
				gbc_brailleText.gridy = 0;
				panel.add(brailleText, gbc_brailleText);
				brailleText.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			GridBagConstraints gbc_buttonPane = new GridBagConstraints();
			gbc_buttonPane.anchor = GridBagConstraints.NORTH;
			gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
			gbc_buttonPane.gridx = 0;
			gbc_buttonPane.gridy = 1;
			getContentPane().add(buttonPane, gbc_buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(brailleText.getText().length() > Scenario.getBrailleCells()) {
							JOptionPane.showMessageDialog(null, "You can only enter up to "+Scenario.getBrailleCells()+" chracters.", "Character Limit Exceeded",
					                JOptionPane.ERROR_MESSAGE);
						} else {
							Scenario.bufferWriteBraille(brailleText.getText());
							blockBuilder.refreshBuffer();
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
