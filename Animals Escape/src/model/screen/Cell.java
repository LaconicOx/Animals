package model.screen;

import java.util.Objects;

import model.ModelParameters;
import model.ModelParameters.Direction;
import model.board.Node;

public abstract class Cell implements Comparable<Cell>{
	
	protected Node node;
	protected ModelParameters parameters;
	protected double[] center;
	
	public Cell(Node node) {
		System.err.println(node);
		this.node = node;
		node.setCell(this);
		parameters = ModelParameters.getInstance();
		center = parameters.nodeToModel(node.getCenter());
		System.err.println(center);
	}
	
	
	////////////////////////// Accessor Methods //////////////////////////
	
	public double[] getCenter() {
		return center.clone();
	}
	
	/**
	 * Clears node of callback to this cell.
	 */
	public void clearNode() { node.clearCell(); }
	
	public Node getNode() { return node; }
	
	public Cell getNeighborCell(Direction dir, boolean nullReturn) {
		/*
		 * Returns reference to neighboring cell if found.
		 * Returns null if no neighboring node  exists or.
		 * if neighboring node contains no cell.
		 */
		
		Node n = node.getNeighbor(dir);
		Cell c = n.getCell();
		//System.err.println(c);
		if (nullReturn || c != null)
			return c;
		else
			return new ScreenCell(n);
		
	}
	
	public Node getNeighborNode(Direction dir){ return node.getNeighbor(dir); }
	
	public void getCommand() {node.getCommand(getCenter());}
	
	
	//////////////////////// Abstract Methods ///////////////////////////
	
	
	public abstract boolean isContained(double[] coords);

	public abstract boolean isPassable();


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
}
