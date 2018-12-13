package model.screen;

import java.util.Objects;

import game.Directions.Direction;
import model.keys.CellKey;

public abstract class Cell implements Comparable<Cell>{
	
	private final CellKey key;
	
	public Cell(CellKey key) {
		this.key = key;
	}
	
	////////////////////////// Mutator Methods ///////////////////////
	
	public abstract void draw();
	
	////////////////////////// Accessor Methods //////////////////////////
	
	public double[] getCenter() {
		return key.getCenter();
	}
	
	public CellKey getKey() {
		return key;
	}
	
	public CellKey getNeighborKey(Direction dir){ return key.getNeighborKey(dir); }
	
	public boolean isContained(double[] coords) {
		return key.containsPoint(coords);
	};
	
	/////////////////////////// Checker Methods //////////////////////////
	
	public boolean checkDeer() {
		return key.checkDeer();
	}
	
	public boolean checkPassable() {
		return key.checkPassable();
	}
	
	public boolean containsPoint(double[] point) {
		return key.containsPoint(point);
	}

	/////////////////////// Overrides /////////////////////////////////////
	
	@Override
	public int compareTo(Cell other) {
		/*
		 * Cells are first ordered by columns and then by rows.
		 * Thus, the top-left cell is the very least, while the bottom-right cell
		 * is the very greatest.
		 */
		//The component grid causes problems for comparisons by making points to the top
		//or to the left of the screen negative. This thwarts the natural comparison of 
		//the screen via positive numbers. The easiest way to correct for it is to shift
		//the coordinates enough so that only positive values obtain.
		
		
		final double SHIFT = 10000.0;
		
		double[] curCenter = getCenter();
		double currentX = curCenter[0] + SHIFT;
		double currentY = curCenter[1] + SHIFT;
		
		double[] othCenter = other.getCenter();
		double otherX = othCenter[0] + SHIFT;
		double otherY = othCenter[1] + SHIFT;
		
		if (0 > currentX - otherX)
			return -1;
		else if (0 < currentX - otherX)
			return 1;
		else{
			if (0 > currentY - otherY)
				return -1;
			else if (0 < currentY - otherY)
				return 1;
			else
				return 0;
		}
	}
	
	@Override
	public boolean equals(Object ob) {
		double[] center = getCenter();
		if (ob.getClass() != this.getClass())
			return false;
		Cell obCell = (Cell)ob;
		double[] obCenter = obCell.getCenter();
		if((center[0] == obCenter[0]) && (center[1] == obCenter[1]))
			return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		double[] center = getCenter();
		return Objects.hash(center);
		
	}
	
	///////////////////////// Debugging ////////////////////////////////
	
	public abstract void display();

	
}//End of Cell
