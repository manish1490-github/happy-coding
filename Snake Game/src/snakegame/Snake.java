/**
 * 
 */
package snakegame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import snakegame.exceptions.UnallowedMoveException;

/**
 * @author manishym
 *
 */
public class Snake {
	private List<Point> snakeBody;
	
	/**
	 * @param snakeBody
	 * @param symbol
	 */
	public Snake(Point startPosition) {
		snakeBody = new ArrayList<Point>();
		this.snakeBody.add(startPosition);
	}

	/**
	 * @return the snakeBody
	 */
	public List<Point> getSnakeBody() {
		return snakeBody;
	}
	
	/**
	 * @param direction
	 * @param gotFood
	 * @throws UnallowedMoveException
	 */
	public void move(Direction direction, boolean gotFood) {
		Point newHead = getNextPoint(direction);
		if(!willDie(direction)) {
			snakeBody.add(newHead);
			if(!gotFood) {
				snakeBody.remove(0);
			}
		}
	}
	
	public boolean willDie(Direction direction) {
		Point point = getNextPoint(direction);
		if(Game.isInsideGameWindow(point)) {
			if(!hasCollidedWithSnake(point)) {
				return(false);
			} else {
//				throw(new UnallowedMoveException("Game Over, Snake touched its body."));
			}
			
		} else {
//			throw(new UnallowedMoveException("Game Over, Snake touched border."));
		}
		return(true);
	}
	
	public Point getNextPoint(Direction direction) {
		Point point = getHeadPosition();
		Point newHead;
		switch(direction) {
			case NORTH:
				newHead = new Point(point.x, point.y - 1);
				break;
			case SOUTH:
				newHead = new Point(point.x, point.y + 1);
				break;
			case EAST:
				newHead = new Point(point.x + 1, point.y);
				break;
			case WEST:
				newHead = new Point(point.x - 1, point.y);
				break;
			default:
				return point;
		}
		return(newHead);
	}
	
	private boolean hasCollidedWithSnake(Point newHead) {
		for(Point point : snakeBody) {
			if(point.x == newHead.x && point.y == newHead.y) {
				return(true);
			}
		}
		return false;
	}
	
	public boolean willGetFood(Food food, Direction direction) {
		Point point = getNextPoint(direction);
		Point foodPosition = food.getLocation();
		if(point.x == foodPosition.x && point.y == foodPosition.y) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 */
	public Point getHeadPosition() {
		Point point = snakeBody.get(snakeBody.size() - 1);
		return(point);
	}
	
	/**
	 * @return
	 */
	public Point getTailPosition() {
		Point point = snakeBody.get(0);
		return(point);
	}
}
