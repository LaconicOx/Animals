package model.characters;


import game.Directions.Direction;
import model.screen.Cell;

public abstract class Characters {
	
	protected Cell cell;//cell containing the character.
	protected Direction facing;//the direction the animation should be facing/moving.
	protected double[] center;//Character's center in model units.
	protected double[]  moveVector;//unit vector for movement.
	
	public Characters(Cell cell) {
		this.cell = cell;
		this.center = cell.getCenter();
	}
	
	//////////////////// Mutator Methods ////////////////////////
	
	
	public abstract void update();
	
	//////////////////// Checker Methods ////////////////////////
	
	
	
	public boolean offScreen(Cell test) {
		if (test.equals(cell))
			return true;
		else
			return false;
	}
	
	
}
