package main;

import javax.swing.JFrame;

public class App2 {

	public static void main(String[] args) {
		int boardWidth = 360;
		int boardHeight = 640;
		
		
		//this is where we build the frame of the game using jframe
		JFrame frame = new JFrame("FLAPPY BIRD"); //creates the frame object and name of game
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(boardWidth,boardHeight);
		frame.setResizable(true); //user cannot resize the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes game when x button is clicked

	}

}
