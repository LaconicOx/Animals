package model.characters;

import commands.swing.CommandFactory;
import model.Directions.Direction;
import model.screen.Cell;
import model.screen.Screen;
import units.CellKey;
import units.ImageKey;

public class Player{
	
	private static final double SPEED = 0.01;
	
	private Cell cell;
	private Direction facing;//the direction the animation should be facing.
	private double[] center;//Character's center.
	private Screen screen;
	
	public Player(Cell cell) {
		this.cell = cell;
		this.center = cell.getCenter();
		screen = Screen.getInstance();
	}
	
	//////////////////// Mutator Methods ////////////////////
	
	/**
	 * 
	 * @return - true if cell were updated, otherwise returns false.
	 */
	public void move(double angle) {
		double[] destination = new double[] {center[0] + SPEED * Math.cos(angle), center[1] - SPEED * Math.sin(angle)};
		
		if(cell.isContained(destination)) {
			center = destination;
			ImageKey.updateShift(getCenter());
			CommandFactory.getPingView();//TODO - temporary fix to mix player.
		}
		else{
			Direction toward = Direction.getDirection(cell.getCenter(), destination);
			CellKey key = cell.getNeighborKey(toward);
			//Tests whether cell is passable.
			if(key.checkPassable()) {
				center = destination;
				ImageKey.updateShift(getCenter());
				cell = screen.getNeighborCell(key);
				screen.shiftCells(toward);
			}
		}
	}
	
	public double[] getCenter() {
		return center.clone();
	}
	
	public void update(){
		
	}//TODO
	
	
	
}//End of Player
