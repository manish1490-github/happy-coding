/**
 * 
 */
package snakegame;

import java.awt.Point;

/**
 * @author manishym
 *
 */
public class Food {
	private Point location;
	private int value;
	
	/**
	 * @param location
	 * @param symbol
	 * @param value
	 */
	public Food(Point location, int value) {
		this.location = location;
		this.value = value;
	}
	
	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
