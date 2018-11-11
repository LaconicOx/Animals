package model.characters;


import model.Directions.Direction;
import model.screen.Cell;

public abstract class Characters implements Comparable<Characters>{
	
	private static final double speed = 0.1;
	
	protected Cell cell;//cell containing the character.
	protected Direction facing;//the direction the animation should be facing.
	protected double[] center;//Character's center.
	protected double[]  moveVector;//unit vector for movement.
	
	public Characters(Cell cell) {
		this.cell = cell;
		this.center = cell.getCenter();
	}
	
	//////////////////// Mutator Methods ////////////////////////
	
	
	
	
	//////////////////// Checker Methods ////////////////////////
	
	
	
	public boolean offScreen(Cell test) {
		if (test.equals(cell))
			return true;
		else
			return false;
	}
	
	public abstract void update();
}
