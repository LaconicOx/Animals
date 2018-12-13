package units;

import model.Directions.Direction;
import model.screen.Cell;

public abstract class CharacterKey extends ModelKey{
	
	//dim[0] represents the character's width in model units.
	//dim[1] represent the character's height in model units.
	private double[] dim;
	private double[] center;
	private Direction facing;
	
	protected CharacterKey(Cell cell, double[] dim) {
		this.dim = dim;
		center = cell.getCenter();
		facing = Direction.N;
	}
	
	//////////////////////////// Accessor Methods ///////////////////////////
	
	public double[] getCenter() {
		return center.clone();
	}
	
	public double[] getDimensions() {
		return dim;
	}
	
	////////////////////////// Mutator Methods ///////////////////////////////
	
	public void setCenter(double[] coords) {
		stepCount();
		center[0] = coords[0];
		center[1] = coords [1];
	}
	
	/////////////////////////// Checker Methods //////////////////////////////
	/**
	 * 
	 * @param coord - point to be tested
	 * @return True if character contains point; otherwise False.
	 */
	public boolean contains(double[] coord) {
		
		if (coord[0] >= center[0] - dim[0]/2 && coord[0] <= center[0] + dim[0]/2) {
			if (coord[1] >= center[1] - dim[1]/2 && coord[1] <= center[1] + dim[1]/2) {
				return true;
			}
			else {
				return false;
			}
		}
		else
			return false;
	}
	
}
