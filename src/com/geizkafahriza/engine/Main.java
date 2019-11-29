package com.geizkafahriza.engine;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		GameEngine game = new GameEngine();
		
		JFrame window = new JFrame("Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		game.StartEngine();
		window.setResizable(false);

	}

}
