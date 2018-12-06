package model.characters;

import commands.swing.CommandFactory;
import model.Directions.Direction;
import model.screen.Cell;
import model.screen.Screen;
import units.CellKey;
import units.ImageKey;
import units.PlayerKey;

public class Player{
	
	private static final double STRIDE = 0.015;
	private static final int FRAMES = 25;
	
	private Cell cell;
	private Screen screen;
	private PlayerKey key;
	
	public Player(Cell cell) { 
		this.cell = cell;
		key = new PlayerKey(cell);
		screen = Screen.getInstance();

		CommandFactory.getDrawBob(key);
	}
	
	//////////////////// Mutator Methods ////////////////////
	
	
	/**
	 * 
	 * @return - true if cell were updated, otherwise returns false.
	 */
	public void move(double angle) {
		//Player's position before being moved.
		double[] center = key.getCenter();
		//Player's destination position
		double[] destination = new double[] {center[0] + STRIDE * Math.cos(angle), center[1] - STRIDE * Math.sin(angle)};
		
		if(cell.isContained(destination)) {
			key.setCenter(destination);;
			ImageKey.updateShift(destination);
		}
		else{
			Direction toward = Direction.getDirection(cell.getCenter(), destination);
			CellKey newCell = cell.getNeighborKey(toward);
			//Tests whether cell is passable.
			if(newCell.checkPassable()) {
				key.setCenter(destination);
				ImageKey.updateShift(destination);
				cell = screen.getNeighborCell(newCell);
				screen.shiftCells(toward);
			}
		}
	}
	
	public void update(){
		//if (key.getCount() % FRAMES != 0) {
			//key.stepCount();
			
		//}
			
		
	}
	
	
	
}//End of Player
