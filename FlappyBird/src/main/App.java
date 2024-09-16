package main;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		int boardWidth = 360;
		int boardHeight = 640;
		
		JFrame frame = new JFrame("flappy Bird");//clss
		//frame.setVisible(true);//makes frame visible
		frame.setLocationRelativeTo(null);//sets window at center of screen
		frame.setSize(boardWidth,boardHeight);//size of window without it no window appears (backgrownd is that size that is why
		frame.setResizable(false);//user cannot resize the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when user clicks on x button program is terminated
		
		
		//create flappy bird object
		FlappyBird flappyBird = new FlappyBird();
		frame.add(flappyBird);
		frame.pack();//excludes title bar
		frame.setVisible(true);
		flappyBird.requestFocus();
	}

}
