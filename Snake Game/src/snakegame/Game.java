/**
 * 
 */
package snakegame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author manishym
 *
 */
public class Game {
	private static Snake snake;
	private static Food food;
	private static int score;
	private static Direction direction;

	private static int scoreboardHeight;
	private static int borderThickness;
	private static int foodPoints;
	
	private static Point scoreBoardStart;
	private static Point gameWindowStart;
	
	public static void run() {
		prepareNewGame();
		playGame();
		Screen.clearScreen();
//		Screen.displayScreen(display);
	}
	
	private static void playGame() {
		while(true) {
			Screen.displayScreen();
			char c = Screen.getInput();
			switch(c) {
			case 'q':
				return;
			case 'w':
				if(direction != Direction.SOUTH) {
					direction = Direction.NORTH;
				}
				break;
			case 's':
				if(direction != Direction.NORTH) {
					direction = Direction.SOUTH;
				}
				break;
			case 'a':
				if(direction != Direction.EAST) {
					direction = Direction.WEST;
				}
				break;
			case 'd':
				if(direction != Direction.WEST) {
					direction = Direction.EAST;
				}
				break;
			}
			
			if(snake.willDie(direction)) {
				gameOver();
				return;
			} else {
				boolean gotFood = snake.willGetFood(food, direction);
				
				if(gotFood) {
					updateScore();
					prepareNewFood();
				}
				
				Point snakeTail = snake.getTailPosition();
				snake.move(direction, gotFood);
				Point snakeHead = snake.getHeadPosition();

				Screen.putChar(gameWindowStart.x + snakeHead.x, gameWindowStart.y + snakeHead.y, Symbol.SNAKE.getValue());
				if(!gotFood) {
					Screen.putChar(gameWindowStart.x + snakeTail.x, gameWindowStart.y + snakeTail.y, Symbol.EMPTY.getValue());
				}
			}
		}
	}
	
	private static void prepareNewGame() {
		scoreboardHeight = 3;
		borderThickness = 1;

//		displayHeight = Screen.titleMargin + gameTitleHeight + Screen.titleMargin + 
//						borderThickness + scoreboardHeight + borderThickness +
//						Screen.titleMargin + 
//						borderThickness + Screen.WINDOW_HEIGHT + borderThickness;
//
//		displayWidth = 	borderThickness + Screen.WINDOW_WIDTH + borderThickness;
		foodPoints = 5;
		
		score = 0;
		direction = Direction.NORTH;
		
		//Prepare Display
		Screen.initializeScreen();
		prepareInitialGameDisplay();
		prepareNewSnake();
		prepareNewFood();
	}
	
	private static void prepareInitialGameDisplay() {
		scoreBoardStart = new Point(borderThickness, borderThickness);
		gameWindowStart = new Point(scoreBoardStart.x, scoreBoardStart.y + scoreboardHeight + borderThickness * 2);
		
		//Adding border for scoreboard
		Screen.displayBorder(scoreBoardStart.x - borderThickness, scoreBoardStart.y - borderThickness, scoreboardHeight + borderThickness * 2, Screen.WINDOW_WIDTH + borderThickness * 2, borderThickness);
		
		//Adding score to scoreboard
		displayScore(); 
		
		//Adding border for game window
		Screen.displayBorder(gameWindowStart.x - borderThickness, gameWindowStart.y - borderThickness, Screen.WINDOW_HEIGHT + borderThickness * 2, Screen.WINDOW_WIDTH + borderThickness * 2, borderThickness);
	}
	
	private static void prepareNewFood() {
		food = new Food(getRandomPoint(), foodPoints);
		displayFood();
	}
	
	private static void prepareNewSnake() {
		Point startPoint = getSnakeStartPoint();
		snake = new Snake(startPoint);
		Screen.putChar(gameWindowStart.x + startPoint.x, gameWindowStart.y + startPoint.y, Symbol.SNAKE.getValue());
	}
	
	private static void displayScore(){
		String scoreText = "Score: " + score;
		int len = scoreText.length();
		int startX = scoreBoardStart.x + ((Screen.WINDOW_WIDTH - len) / 2);
		int startY = scoreBoardStart.y + (scoreboardHeight  / 2);

		for(int i = 0; i < len; i++) {
			Screen.putChar(startX + i, startY, scoreText.charAt(i));
		}
	}
	
	private static void displayFood(){
		Point point = food.getLocation();
		Screen.putChar(gameWindowStart.x + point.x, gameWindowStart.y + point.y, Symbol.FOOD.getValue());
	}
	
	private static List<Point> getEmptyPoints() {
		int startX = gameWindowStart.x;
		int startY = gameWindowStart.y;
		List<Point> emptyPoints = new ArrayList<Point>();
		
		for(int i = 0; i < Screen.WINDOW_HEIGHT; i++) {
			for(int j = 0; j < Screen.WINDOW_WIDTH; j++) {
				if(Screen.getChar(startX + j, startY + i) == Symbol.EMPTY.getValue()) {
					emptyPoints.add(new Point(j, i));
				}
			}
		}
		
		return(emptyPoints);
	}
	
	private static Point getRandomPoint() {
		List<Point> emptyPoints = getEmptyPoints();
		int randomPoint = new Random().nextInt(emptyPoints.size());
		return(emptyPoints.get(randomPoint));
	}
	
	private static Point getSnakeStartPoint() {
		int x = Screen.WINDOW_WIDTH/2;
		int y = Screen.WINDOW_HEIGHT/2;
		return(new Point(x, y));
	}
	
	public static boolean isInsideGameWindow(Point point) {
		if(point.x < Screen.WINDOW_WIDTH && point.y < Screen.WINDOW_HEIGHT && point.x >= 0 && point.y >= 0) {
			return(true);
		}
		return false;
	}
	
	private static void updateScore() {
		if(snake.willGetFood(food, direction)){
			score += food.getValue();
			displayScore();
		}
	}
	
	private static void gameOver() {
		Screen.initializeScreen();
		
		//Adding border for scoreboard
		Screen.displayBorder(scoreBoardStart.x - borderThickness, scoreBoardStart.y - borderThickness, Screen.WINDOW_HEIGHT + borderThickness * 2, Screen.WINDOW_WIDTH + borderThickness * 2, borderThickness);

		int startY = scoreBoardStart.y + ((Screen.WINDOW_HEIGHT - 3)  / 2);
		String text = "GAME OVER";
		int len = text.length();
		int startX = scoreBoardStart.x + ((Screen.WINDOW_WIDTH - len) / 2);
		for(int i = 0; i < len; i++) {
			Screen.putChar(startX + i, startY, text.charAt(i));
		}
		
		startY += 2;
		text = "Final Score: " + score;
		len = text.length();
		startX = scoreBoardStart.x + ((Screen.WINDOW_WIDTH - len) / 2);
		for(int i = 0; i < len; i++) {
			Screen.putChar(startX + i, startY, text.charAt(i));
		}
		
		Screen.displayScreen();
		Screen.delay(2000);
	}
	
	/**
	 * @return the snake
	 */
	public Snake getSnake() {
		return snake;
	}
	
	/**
	 * @return the food
	 */
	public Food getFood() {
		return food;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
}
