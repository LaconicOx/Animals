package model.keys;

import java.util.Objects;


/*
 * I wrote this class to replace the Point class in the awt library. I wanted the list immutable 
 * and limited to two components to protect against future runtime errors. The main use of this 
 * class will be as keys for HashMap entries. 
 */
public class NodeKey{
	
	private int[] center;
	
	public NodeKey(int[] coords) {
		
		if (!checkKey(coords)) {
			System.exit(0);
		}
		else {
			
			center = coords;
		}
		
	}
	
	///////////////////// Private Methods ///////////////////////////
	/**
	 * Tests coordinates. Returns true for (even, even) and (odd, odd).
	 * Returns false for (even, odd) and (odd, even).
	 */
	private boolean checkKey(int[] center) {
		
		//Applies test condition described in java docs.
		if ((center[0] * center[1] % 2 != 0)||((center[0] % 2 == 0)&&(center[1] % 2 == 0))) {
			return true;
		}
		else {
			System.err.println("(" + center[0] + ","+ center[1] + ") is an invald node key. Must be formatted as (even, even) or (odd, odd).");
			return false;
		}
	}
	
	/////////////////////// Public Methods //////////////////////////////
	public int[] getCenter() {
		return center.clone();
	}
	
	///////////////////// Overrides //////////////////////////////////
	
	@Override
	public boolean equals(Object o) {
		//Gates against possible errors from comparing unlike objects.
		if (o.getClass() != this.getClass())
			return false;
		NodeKey p = (NodeKey) o;
		int[] otherCenter = p.getCenter();
		if (center[0] == otherCenter[0] && center[1] == otherCenter[1])
			return true;
		else { 
			return false;
		}
			
	}
	
	@Override
	public int hashCode() {
		//Must hash on the primitives for different keys with the same 
		//coordinates to be equal. Hashing on the int array caused a bug.
		return Objects.hash(center[0], center[1]);
		
	}
	
	@Override
	public String toString() {
		return "(" + center[0] + "," + center[1] + ")";
	}
	
}


