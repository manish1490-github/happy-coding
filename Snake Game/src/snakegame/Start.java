/**
 * 
 */
package snakegame;

import java.awt.Point;

/**
 * @author manishym
 *
 */
public class Start {
	
	private static MenuOption[] menu;
	private static int currentOption;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		prepareMenu();
		displayMenu();
	}
	
	private static void prepareMenu() {
		int startX = 1;
		int startY = 0;
		
		String[] menuSet = {"New Game",
							"Exit"
							};
		
		menu = new MenuOption[menuSet.length];
		for(int i = 0; i < menuSet.length; i++) {
			Point position = new Point(startX + 4, startY + 4 + 2 * i);
			MenuOption option = new MenuOption(i + 1, menuSet[i], position);
			menu[i] = option;
		}
		
		Screen.initializeScreen();
		Screen.displayBorder(startX, startY, Screen.WINDOW_HEIGHT, Screen.WINDOW_WIDTH, 1);
		for(int i = 0; i < menu.length; i++) {
			displayMenuOption(menu[i]);
		}
		currentOption = 0;
		refreshPointer(0);
		Screen.displayScreen();
	}

	private static void displayMenu() {
		int prevOpt;
		
		while(true) {
			Screen.displayScreen();
			prevOpt = currentOption;
			char c = Screen.getInput();
			switch(c) {
				case 'w':
					currentOption = prevOption(currentOption);
					refreshPointer(prevOpt);
					break;
				case 's':
					currentOption = nextOption(currentOption);
					refreshPointer(prevOpt);
					break;
				case 'o':
					switch(menu[currentOption].getSerialNo()) {
						case 1:
							Game.run();
							prepareMenu();
							break;
						case 2:
							Screen.clearScreen();
							return;
					}
					break;
			}
		}
	}
	
	private static void refreshPointer(int prevOpt) {
		Point position = menu[prevOpt].getPosition();
		Screen.putChar(position.x - 2, position.y, ' ');
		
		position = menu[currentOption].getPosition();
		Screen.putChar(position.x - 2, position.y, '>');
	}
	
	private static int prevOption(int pointer) {
		pointer--;
		if(pointer < 0) pointer += menu.length;
		return pointer;
	}
	
	private static int nextOption(int pointer) {
		return (pointer + 1) % menu.length;
	}
	
	private static void displayMenuOption(MenuOption menuOption) {
		Point position = menuOption.getPosition();
		String text = menuOption.getSerialNo() + ". " + menuOption.getText();
		for(int i = 0; i < text.length(); i++) {
			Screen.putChar(position.x + i, position.y, text.charAt(i));
		}
	}

}
