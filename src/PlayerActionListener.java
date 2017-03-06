package player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerActionListener implements ActionListener {
	
    @Override
    public void actionPerformed(ActionEvent e) {
        // Do stuff in here to handle button presses.
        System.out.println(e.getActionCommand());
    }

}
