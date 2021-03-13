/**
 * 
 */
package snakegame;

import java.awt.Point;

/**
 * @author manishym
 *
 */
public class MenuOption {
	private int serialNo;
	private String text;
	private Point position;

	/**
	 * @param serialNo
	 * @param text
	 * @param position
	 */
	public MenuOption(int serialNo, String text, Point position) {
		this.serialNo = serialNo;
		this.text = text;
		this.position = position;
	}

	/**
	 * @return the serialNo
	 */
	public int getSerialNo() {
		return serialNo;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}
}
