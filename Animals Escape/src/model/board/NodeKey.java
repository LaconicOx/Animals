package model.board;

import java.util.Objects;

/*
 * I wrote this class to replace the Point class in the awt library. I wanted the list immutable 
 * and limited to two components to protect against future runtime errors. The main use of this 
 * class will be as keys for HashMap entries. 
 */
public class NodeKey{
	private int x, y;
	
	public NodeKey(int x, int y) {
		if (!checkKey(x, y)) {
			System.err.println("(" + x + ","+ y + ") is an invald node key. Must be formatted as (even, even) or (odd, odd).");
			System.exit(0);
		}
		else {
			this.x = x;
			this.y = y;
		}
		
	}
	
	NodeKey(int[] coords) {
		int x = coords[0];
		int y = coords[1];
		if (!checkKey(x, y)) {
			System.err.println("(" + x + ","+ y + ") is an invald node key. Must be formatted as (even, even) or (odd, odd).");
			System.exit(0);
		}
		else {
			this.x = x;
			this.y = y;
		}
		
	}
	
	///////////////////// Private Methods ///////////////////////////
	
	private boolean checkKey(int x, int y) {
		/*
		 * Tests coordinates. Returns true for (even, even) and (odd, odd).
		 * Returns false for (even, odd) and (odd, even).
		 */
		if ((x * y % 2 != 0)||((x % 2 == 0)&&(y % 2 == 0)))
			return true;
		else return false;
	}
	
	/////////////////////// Public Methods //////////////////////////////
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	///////////////////// Overrides //////////////////////////////////
	
	@Override
	public boolean equals(Object o) {
		//Gates against possible errors from comparing unlike objects.
		if (o.getClass() != this.getClass())
			return false;
		NodeKey p = (NodeKey) o;
		if (x == p.getX() && y == p.getY())
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
		
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}


