package model.characters;

import model.screen.Cell;
import model.screen.Screen;

public class CharactersFactory {
	private Screen screen;
	private static CharactersFactory unique = null;
	
	private CharactersFactory() {
		screen = Screen.getInstance();
	}
	
	public static CharactersFactory getInstance() {
		if (unique == null)
			unique = new CharactersFactory();
		return unique;
	}
	
	public void initDeer(Cell cell) {
		screen.addCharacters(new Deer(cell));
	}
}
