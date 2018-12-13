package model.characters;

import model.screen.Cell;
import model.screen.Screen;

public class CharactersFactory {
	private Screen screen;
	
	private CharactersFactory(Screen screen) {
		this.screen = screen;
	}
	
	public void initDeer(Cell cell) {
		screen.addCharacters(new Deer(cell));
	}
}
