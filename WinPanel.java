import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class WinPanel extends JPanel implements ActionListener {
    /*variables Used in this Class*/
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int SPEED = 100;
	final int y[] = new int[GAME_UNITS];
	final int x[] = new int[GAME_UNITS];

	int foodEaten, foodX, foodY, bodyUnits = 3;

	char direction = 'R';
	boolean gameRunning = false;
	Timer timer;
	Random random;

   /*the WinPanel Constructor*/
	WinPanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();

	}

    //Start Game Method To Initialize The Game
	public void startGame() {
		gameRunning = true;
		newFood();
		timer = new Timer(SPEED, this);
		timer.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}

	public void draw(Graphics g) {
 //If Game Is Running Then Program Draw Objects
		if(gameRunning) {
	
	
	/*Remove Comments to Use Gridlines		
			
			  //GRIDLINES on the main background. Enjoy 
			for (int i = 0; i < SCREEN_HEIGHT /UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
			  g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); }
			 
			 
			 */
			
				g.setColor(Color.red);
				g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			
				for (int i =0; i < bodyUnits; i++) {
					if(i == 0) {
						g.setColor(Color.green);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					
					}
				
					else {
						
						/*Remove Coments To Use The MulticoloredSnake
						
						
						  //FredyD3v easter Egg: "MultiColoured Snake". Remove Comments and enjoy.
						  g.setColor(new Color(random.nextInt(255), random.nextInt(255),
						  random.nextInt(255)));
						  
						  */
						  
						g.setColor(new Color(48, 182, 0));
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);	
					}
				}
				
				//Count Player Scores
				g.setColor(Color.red);
				g.setFont(new Font("Ink Free", Font.BOLD, 15));
				FontMetrics metric = getFontMetrics(g.getFont());
				g.drawString("Score: "+foodEaten, (SCREEN_WIDTH - metric.stringWidth("Score: "+foodEaten))/2, g.getFont().getSize());
		
			
		} else { 
				  
				  gameOver(g);
				  
				  }
		
	}

    //Creates A Food Object Randomly On Screen
	public void newFood() {
		foodX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		foodY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

	}

	public void move() {
		for (int i = bodyUnits; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;

		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;

		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;

		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}

	}

	public void checkCollision() {
	    
    //Check Head Right Border Collision
		if (x[0] < 0) {
			gameRunning = false;

		}
		
    //Check Head Left Border Collision
		if (x[0] > SCREEN_WIDTH) {
			gameRunning = false;
		}
    //Check Head Top Border Collision
		if (y[0] > SCREEN_HEIGHT) {
			gameRunning = false;
		}
    //Check Head Bottom Border Collision
		if (y[0] < 0) {
			gameRunning = false;
		}
    //Check Head Body Collision
		for (int i = bodyUnits; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				gameRunning = false;
			}
		}

     //Stops Timer If Game Is Not Running
		if (!gameRunning) {
			timer.stop();
		}

	}

	public void checkFood() {
		if ((x[0] == foodX) && (y[0] == foodY)) {
			bodyUnits++;
			foodEaten++;
			newFood();
		}

	}

	public void gameOver(Graphics g) {
		//Prints GameOver On Screen When Player Dies
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 50));
		FontMetrics metric = getFontMetrics(g.getFont());
		g.drawString("Game Over LOOSER!!", (SCREEN_WIDTH - metric.stringWidth("Game Over LOOSER!!")) / 2, SCREEN_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    /*Refreshes The Screen To Update The Game*/
		if (gameRunning) {
			move();
			checkFood();
			checkCollision();
		}
		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter {
	    
	    //Take Keyboard Input
		@Override
		public void keyPressed(KeyEvent e) {
          //Prevents 180° turns
          //Move Player Left
          
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
				
			//Move player right	
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
				
			//Move Player Up
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
				
			//Move Player Down
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}

		}
	}

}
