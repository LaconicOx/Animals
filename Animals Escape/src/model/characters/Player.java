package model.characters;

import model.ModelParameters;
import model.ModelParameters.Direction;
import model.screen.Cell;

public class Player{
	
	private Cell cell;
	private Direction facing;//the direction the animation should be facing.
	private double[] center;//Character's center.
	ModelParameters parameters;
	
	public Player(Cell cell) {
		this.cell = cell;
		this.parameters = ModelParameters.getInstance();
	}
	
	//////////////////// Mutator Methods ////////////////////
	
	/**
	 * 
	 * @return - true if cell were updated, otherwise returns false.
	 */
	public boolean move(double angle) {
		double[] start = getCenter();//clones the center to get the starting point.
		double speed = parameters.getSpeed();
		center[0] += speed * Math.cos(angle);
		center[1] += speed * Math.sin(angle);
		
		if(cell.isContained(center)) 
			return false;
		else{
			Direction toward = Direction.getDirection(start, center);
			cell = cell.getNeighborCell(toward, true);
			return true;
		}
		//TODO - Send command to update the view.
	}
	
	public double[] getCenter() {
		return center.clone();
	}
	
	public void update(){
		
	}//TODO
	
	
}//End of Player
