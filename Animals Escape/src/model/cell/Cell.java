package model.cell;


import java.util.ArrayList;
import java.util.Objects;

import model.ModelUtility.*;

public abstract class Cell implements Comparable<Cell>{
	
	private int wrap = 0;
	
	
	////////////////////////// Default Methods //////////////////////////
	boolean isWrappable() {return true;}
	
	int getWraps() {return wrap;}
	
	public boolean isOffScreen(Direction dir){
		return false;
		}
	
	Cell wrapCell(Cell c) {return c;}
	
	public Cell getNewBorder(Direction dir) {
		System.err.println("getNewBorder: Unwrapped cell found in border");
		return this;
	}
	
	//////////////////////// Abstract Methods ///////////////////////////
	
	public abstract Cell getCell();//Returns concrete cell.
	public abstract Cell getNeighborCell(Direction dir, boolean nullReturn);
	public abstract double getX();
	public abstract double getY();
	public abstract double[] getCenter();
	public abstract void clearNode();
	public abstract boolean isContained(double x, double y);
	public abstract void getCommand();
	
	
	////////////////////// Static Methods ///////////////////////////////////
	
	public static Cell select(ArrayList<Cell> cells, Edge side) {
		 final int GREATER = 1;
		 final int LESS = -1;
		Cell first = null;
		Cell second = null;
		 
		if(cells.isEmpty()) {
			System.err.println("borderInit selected no cells.");
			System.exit(0);
		}
		else if((side == Edge.TOP || side == Edge.BOTTOM) && cells.size() >2) {
			System.err.println("borderInit selected more than two cells for top or bottom.");
			System.exit(0);
		}
		else if  ((side == Edge.RIGHT || side == Edge.LEFT) && cells.size() > 1) {
			System.err.println("borderInit selected more than one cell for right or left.");
			System.exit(0);
		}
		
		//Conditional for loading cells
		if (cells.size() == 1)
			first = cells.get(0);
		else {
			first = cells.get(0);
			second = cells.get(1);
		}
		//Right and left cells require no comparison
		if(side == Edge.RIGHT || side == Edge.LEFT)
			return first;
		else {
			//First two conditions handle null values in order protect compareTo from nullPointerExceptions.
			if (first == null)
				return second;
			else if(second == null)
				return first;
			else if (side == Edge.TOP) {
				//By the comparison rule for cells, the top cells are less than bottom cells.
				if(first.compareTo(second) == LESS)
					return first; 
				else
					return second;
			}
			else {//else represents BOTTOM.
				//By the comparison rule for cells, the bottom cells are greater than top cells.
				if(first.compareTo(second) == GREATER)
					return first;
				else
					return second;
			}
		}
	}
	
	
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
		return Objects.hash(center, wrap);
		
	}
	
	///////////////////////// Debugging ////////////////////////////////
	
	public abstract void display();
}
