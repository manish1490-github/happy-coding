/**
 * 
 */
package snakegame;

/**
 * @author manishym
 *
 */
public enum Symbol {
	SNAKE('O'), FOOD('$'), BORDER('*'), EMPTY(' ');
	
	private char value;

	/**
	 * @param value
	 */
	private Symbol(char value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public char getValue() {
		return value;
	}
}
