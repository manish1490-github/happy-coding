/**
 * 
 */
package snakegame;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author manishym
 *
 */
public class Screen {
	static final int WINDOW_WIDTH = 50;
	static final int WINDOW_HEIGHT = 20;
	static final int DISPLAY_HEIGHT = 40;
	static final int DISPLAY_WIDTH = 100;
	private static char[][] display;

	private static DataInputStream dis;
	private static Point gameTitleStart;
	private static Point gameWindowStart;
	private static int titleMargin;
	private static int titleHeight;
	
	/**
	 * This method is used to create new screen.
	 */
	public static void initializeScreen() {
		display = new char[DISPLAY_HEIGHT][DISPLAY_WIDTH];
		for(int i = 0; i < display.length; i++) {
			Arrays.fill(display[i], Symbol.EMPTY.getValue());
		}
		
		titleMargin = 1;
		titleHeight = 1;
		gameTitleStart = new Point(titleMargin, titleMargin);
		gameWindowStart = new Point(titleMargin, titleMargin * 2 + titleHeight);
		displayGameTitle();
		
		dis = new DataInputStream(System.in);
	}
	
	/**
	 * This method is used to display character the screen.
	 */
	public static void putChar(int x, int y, char character) {
		display[gameWindowStart.y + y][gameWindowStart.x + x] = character;
	}
	
	/**
	 * This method is used to get character on screen.
	 */
	public static char getChar(int x, int y) {
		return display[gameWindowStart.y + y][gameWindowStart.x + x];
	}

	/**
	 * This method is used to clear the screen.
	 */
	public static void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	/**
	 * @param display
	 * This method is used to display grid on the screen.
	 */
	public static void displayScreen() {
		clearScreen();
		for(int i = 0; i < display.length; i++) {
			System.out.println(new String(display[i]));
		}
	}

	public static void displayBorder(int startX, int startY, int height, int width, int borderThickness) {
		int endX = startX + width - 1;
		int endY = startY + height - 1;

		for(int i = 0; i < borderThickness; i++) {
			for(int j = startX; j <= endX; j++) {
				putChar(j, startY + i, Symbol.BORDER.getValue());
				putChar(j, endY - i, Symbol.BORDER.getValue());
			}
		}
		for(int i = startY + 1; i < endY; i++) {
			for(int j = 0; j < borderThickness; j++) {
				putChar(startX + j, i, Symbol.BORDER.getValue());
				putChar(endX - j, i, Symbol.BORDER.getValue());
			}
		}
	}
	
	private static void displayGameTitle(){
		String title = "SNAKE GAME";
		int len = title.length();
		int startX = gameTitleStart.x + ((WINDOW_WIDTH - len) / 2);
		int startY = gameTitleStart.y + (titleHeight  / 2);

		for(int i = 0; i < len; i++) {
			display[startY][startX + i] = title.charAt(i);
		}
	}
	
	public static char getInput() {
		char c = '\0';
		try {
			delay(200);
			if(dis.available() > 0) {
				c = (char)dis.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(c);
	}
	
	public static void delay(int milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
