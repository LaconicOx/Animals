package model.screen;

import java.util.Objects;

import model.ModelParameters;
import model.ModelUtility.*;
import model.board.Node;

public abstract class Cell implements Comparable<Cell>{
	
	protected Node node;
	protected ModelParameters parameters;
	
	public Cell(Node node) {
		assert node != null;
		this.node = node;
		node.setCell(this);
		parameters = ModelParameters.getInstance();
	}
	
	
	////////////////////////// Accessor Methods //////////////////////////
	
	public double getX() { return parameters.getNodeCellX(node); }
	
	public double getY() { return parameters.getNodeCellY(node); }
	
	/**
	 * Clears node of callback to this cell.
	 */
	public void clearNode() { node.clearCell(); }
	
	public Node getNode() { return node; }
	
	public double[] getCenter(){return new double[] {getX(), getY()};}
	
	public Cell getNeighborCell(Direction dir, boolean nullReturn) {
		/*
		 * Returns reference to neighboring cell if found.
		 * Returns null if no neighboring node  exists or.
		 * if neighboring node contains no cell.
		 */
		
		Node n = node.getNeighbor(dir);
		Cell c = n.getCell();
		if (nullReturn || c != null)
			return c;
		else
			return new ScreenCell(n);
		
	}
	
	public Node getNeighborNode(Direction dir){ return node.getNeighbor(dir); }
	
	public void getCommand() {node.getCommand(getCenter());}
	
	
	//////////////////////// Abstract Methods ///////////////////////////
	
	
	public abstract boolean isContained(double x, double y);

	
	
	////////////////////// Static Methods ///////////////////////////////////
	
	
	public static Direction getCellDirection(Cell c, double[] p) {
		/*
		 * This function uses trigonometry to calculate the player's direction
		 * relative to a cell's center.
		 * 
		 */
		
		double width = p[0] - c.getX();
		double height = c.getY() - p[1];//The intervals must be calculated differently because of the screen orientation.
		
		//Uses trig to find the angle.
		double hypot = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
		double angle;
		if (height > 0)
			if ( width != 0)//Protects against division by zero.
				angle = Math.acos(width / hypot);
			else 
				angle = Math.PI/2;
		else
			if (width != 0)
				angle = 2*Math.PI - Math.acos(width / hypot);
			else
				angle = 3*Math.PI/2;
		

		//Calculates the side of the hexagon by dividing by 60 degrees in radians and rounding up.
		//The sides, counting counter-clockwise, start at 1 for northeast to 6 for southeast.
		int step = (int)Math.ceil(angle/(Math.PI/3));
		Direction dir = null;
		switch(step) {
			case 1: dir = Direction.NE;
				break;
			case 2: dir = Direction.N;
				break;
			case 3: dir = Direction.NW;
				break;
			case 4: dir = Direction.SW;
				break;
			case 5: dir = Direction.S;
				break;
			case 6: dir = Direction.SE;
				break;
		}
		return dir;
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
		
		double currentX = getX() + SHIFT;
		double currentY = getY() + SHIFT;
		double otherX = other.getX() + SHIFT;
		double otherY = other.getY() + SHIFT;
		
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
		if((center[0] == obCell.getX()) && (center[1] == obCell.getY()))
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
}
