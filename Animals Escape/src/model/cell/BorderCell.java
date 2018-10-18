package model.cell;

import java.util.Set;

import model.ModelUtility.*;

abstract class BorderCell extends Cell{
	protected Cell stored;
	private final Set<Direction> opposite;
	private final int MAX_WRAPS = 2;
	
	
	//////////////////////// Constructor /////////////////////////////////
	
	BorderCell(Cell c, Set<Direction> opposite){
		this.stored = c;
		this.opposite = opposite;
		
		//System.out.println(c);
		
		if (!c.isWrappable())
			throw new AssertionError(); 
	}
	
	/////////////////////////// Default Methods //////////////////////////////
	
	public Cell getCell() {
		//Recursive method to retrieve reference to the concrete cell. 
		return stored.getCell();
	}
	
	public Cell getNewBorder(Direction dir) {
		boolean noNull = false;
		return wrapCell(getNeighborCell(dir, noNull));
	}
	
	public Cell getNeighborCell(Direction dir, boolean nullReturn){
		/*
		 * Recursive helper method for returning neighbor of
		 * wrapped cell.
		 */
		
		return stored.getNeighborCell(dir, nullReturn);
		
	}
	
	@Override
	protected int getWraps() { 
		//Recursively counts the number of wraps.
		return stored.getWraps() + 1;
		}
	
	public double getX() {return stored.getX();}
	
	public double getY() {return stored.getY();}
	
	
	public double[] getCenter() {return stored.getCenter();}
	
	public void getCommand() {stored.getCommand();}
	
	public void clearNode() {stored.clearNode();}
	
	
	//////////////////////////// Abstract Methods ///////////////////////////
	
	protected abstract Cell wrapCell(Cell c);
	
	
	///////////////////////// Overrides /////////////////////////////////
	
	@Override
	public boolean isContained(double x, double y) {return stored.isContained(x, y);}
	
	@Override
	public boolean isOffScreen(Direction dir) {
		if(opposite.contains(dir)) {
			//System.out.println("True. Cell: " + stored + ", Direction: " + dir);
			return true;
		}
		else {
			//System.out.println("False. Cell: " + stored + ", Direction: " + dir);
			return stored.isOffScreen(dir);
		}
			
	}
	
	@Override
	protected boolean isWrappable() {
		if(stored.getWraps() < MAX_WRAPS)
			return true;
		else
			return false;
	}
	
	@Override
	public String toString() {
		return stored.toString();
	}
	
	
	////////////////////////////// Debugging ////////////////////////////////
	public void display() {stored.display();}
	
}//End of BorderCell


