package main;
import java.awt.*;//this is where we get the image class
import java.awt.event.*;
import java.util.ArrayList;//stores all the pipes in the game
import java.util.Random;//placing pipes at random positions
import javax.swing.*;

import java.net.MalformedURLException;
import java.net.URL;
//Flappy Bird class will inherit all of JPanel method
public class FlappyBird extends JPanel implements ActionListener, KeyListener{
	int boardWidth = 360;
	int boardHeight = 640;
	//images (the variables will store the image objects)
	Image backgroundImg;
	Image birdImg;
	Image topPipeImg;
	Image bottomPipeImg;
	
	
	//Bird
	int birdX = boardWidth/8;
	int birdY = boardHeight/2;
	int birdWidth = 34;
	int birdHeight = 24;
	
	class Bird{
		int x = birdX;
		int y = birdY;
		int width = birdWidth;
		int height = birdHeight;
		Image img;
		
		Bird(Image img){
			this.img = img;
			
		}

	}
	
	//pipes
	int pipeX = boardWidth;
	int pipeY = 0;
	int pipeWidth = 64;
	int pipeHeight = 384;
	
	class Pipe{
		int x = pipeX;
		int y = pipeY;
		int width = pipeWidth;
		int height = pipeHeight;
		Image img;
		boolean passed = false;// checks if bird has passed pipe
		//constructor
		
		Pipe(Image img){
			this.img = img;
		}
	}
	
	
	
	
	
	//game logic
	Bird bird;
	
	//inorder to move
	int velocityX = -4;
	int velocityY = 0; 
	int gravity = 1 ;// the gravity needs to be set to a positive value
	
	ArrayList<Pipe> pipes;
	Random random = new Random();
	
	
	//timer
	Timer gameLoop;
	Timer placePipesTimer;
	
	
	//gameOver
	boolean gameOver = false;
	
	//score
	double score = 0;
	double highScore = 0;
	
	//constructor- it doesn't have an access modifier like public or private so it has default access(package-private)/( it can only be accessed by other classes in the same package.)
	FlappyBird() {
		setPreferredSize(new Dimension(boardWidth, boardHeight));//the dimension class is part of the java.awt package  It encapsulates the width and height of a component in a single object, 
		setFocusable(true);
		addKeyListener(this);//checks the key listener methods
		//setBackground(Color.blue);
		
		
		//load images 
		backgroundImg = new ImageIcon("file:///C:/Users/zaali/eclipse-workspace/FlappyBird/src/main/flappybirdbg.png").getImage();//getClass refers to the flappy bird class while getResource refers to the location of flappy bird class
		//the .getImage loads the image into the background image variable
		birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
		topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
		bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();	
		
		//bird
		bird = new Bird(birdImg);
		gameLoop = new Timer(1000/60, this); //60 times a second
		gameLoop.start();
		placePipesTimer = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				placePipes();
			}
		});
		placePipesTimer.start();
		pipes = new ArrayList<Pipe>();
		
	
		
		
	
		
		
		
	}
	
	
	
	//method that places pipes
	public void placePipes() {
		//pipeHeight = 512..........pipeHeight/4 = 128.........pipeHeight/2 = 256
		//0-128 -(Random number - 256)
		//math.random returns a double value
		int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
		int openingSpace = boardHeight/4;
		
		
		Pipe topPipe = new Pipe(topPipeImg);
		topPipe.y = randomPipeY;         
		pipes.add(topPipe);// adds the image to the list
		
		Pipe bottomPipe = new Pipe(bottomPipeImg);
		bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
		pipes.add(bottomPipe);
	}
	
	
	
	
	
	
	//move the bird
	public void move() {
		velocityY += gravity;
		bird.y += velocityY;
		bird.y = Math.max(bird.y, 0);// prevent the bird from going above the screen /sets a limit to the height the bird can go to
		bird.y = Math.min(bird.y, 645); 
		
		for(int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			pipe.x += velocityX;//move the pipe every frame by 4 to the left
			if (collision(bird, pipe)) {
				gameOver = true;
			}
			//adds the score count
			if(!pipe.passed && bird.x > pipe.x + pipe.width) {
				pipe.passed = true;
				score += 0.5; //0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
				if (score > highScore) {
					highScore = score;// updates the high score
				}
			}
		}
		//ground
		if (bird.y > boardHeight) {
			gameOver = true;
		}
		
	}
	
	
	
	
	
	//fixed class in java used to paint
	public void paintComponent(Graphics g) {
		//already initialized in jpanel class
		super.paintComponent(g);
		draw(g);
		
	}
	
	
	
	
	
	public void draw(Graphics g) {
		System.out.println("draw");
		g.drawImage(backgroundImg, 0, 640, boardWidth, boardHeight, null);
		
		//bird
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
		
		//pipes
		for(int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			g.drawImage(pipe.img, pipe.x,  pipe.y,  pipe.width,  pipe.height,  null);
		}
		//score
		g.setColor(Color.blue);
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		if(gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over: " + String.valueOf((int)score),10,35);
		}else {
			g.drawString(String.valueOf((int)score),10,35);
		}
		if(score > highScore) {
			g.setColor(Color.green);
			g.drawString("High Score: " + String.valueOf((int)highScore), 10, 45);   
			g.setFont(new Font("Arial", Font.PLAIN, 32));
		}
		
	}
	
	
	
	
	public boolean collision(Bird a, Pipe b) {
		return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
	               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
	               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
	               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
	    
	}
	
	
	
	@Override
	//fixed class in java used when action is being performed
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();//calls the paint component
		if (gameOver) {
			placePipesTimer.stop();// stops placing pipes
			gameLoop.stop();// game stops lpoping
		}
	}
	
	//for the keys

	@Override
	//fixed class in java used when 
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_SPACE) {
			velocityY = -9;
			if(gameOver) {
				//restart game by resetting the conditions
				bird.y = birdY;
				velocityY = 0;
				pipes.clear();//clears pipes in the array
				score = 0;
				gameOver = false;
				gameLoop.start();
				placePipesTimer.start();
			}
		}
		
	}
	@Override 
	public void keyReleased(KeyEvent e) {}
	@Override
	//fixed class in java used when 
	public void keyTyped(KeyEvent e) {}
	
}
