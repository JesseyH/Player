package player;

import simulator.*;

public class Player {
	@SuppressWarnings("unused")
	private static MainViewController controller;
	
	public static void main(String[] args) {
        PlayerActionListener listener = new PlayerActionListener();
       	controller = MainViewController.initialize(listener);
	}

}
