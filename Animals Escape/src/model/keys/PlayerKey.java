package units;

import model.screen.Cell;

public class PlayerKey extends CharacterKey{
	
	private static final double[] DIM = {0.75, 1.5};//Player's dimensions in model units.
	
	public PlayerKey(Cell cell) {
		super(cell, DIM);
	}
	
	
}
