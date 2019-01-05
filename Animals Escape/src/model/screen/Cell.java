package model.screen;

import java.util.Objects;

import game.Directions.Direction;
import model.ModelKey;
import model.board.Node;
import model.board.node_states.CornerTopLeft;
import model.board.node_states.NodeState;

public abstract class Cell implements Comparable<Cell>{
	
	//Class Fields
	
	
	//Instance Fields
	protected final Node node;
	
	public Cell(Node node) {
		this.node = node;
		node.clearWind();
	}
	
	////////////////////////// Mutator Methods ///////////////////////
	
	public abstract void update();
	
	////////////////////////// Accessor Methods //////////////////////////
	
	public double[] getCenter() {
		return node.getCenter();
	}
	
	public ModelKey getKey() {
		return node.getKey();
	}
	
	public ModelKey getNeighborKey(Direction dir){ 
		return node.getNeighborKey(dir); 
	}
	
	/////////////////////////// Checker Methods //////////////////////////
	
	public boolean checkDeer() {
		return node.checkDeer();
	}
	
	public boolean checkPassable() {
		return node.checkPassable();
	}
	
	public boolean checkPoint(double[] point) {
		return node.checkPoint(point);
	};
	
	
	
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

	@Override
	public String toString() {
		return node.toString();
	}
	
}//End of Cell
