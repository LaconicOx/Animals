package model.screen;


import java.util.Objects;

import image_library.Tile;
import model.keys.CellKey;

public class ScreenCell extends Cell {
	
	private final Tile tile;
	
	/////////////////////// Constructor and Initializers ////////////////////////////
	public ScreenCell(CellKey key){
		super(key);
		tile = key.getTile();
		//  initCharacters(); 
	}
	
	/////////////////////////// Mutator Methods ////////////////////////////
	
	public void draw() {
		tile.send();
	}
	
	///////////////////////////// Overrides ///////////////////////////////
	
	@Override
	public String toString() {
		double[] center = getCenter();
		return "ScreenCell (" + center[0] + "," + center[1] + ") maps to Node " + super.toString();
		//return "Cell (" + center[0] + "," + center[1] + ")";
	}
	
	@Override
	public boolean equals(Object ob) {
		double[] curCenter = getCenter();
		if (ob.getClass() != this.getClass())
			return false;
		ScreenCell obCell = (ScreenCell)ob;
		double[] othCenter = obCell.getCenter();
		if((curCenter[0] == othCenter[0]) && (curCenter[1] == othCenter[1]))
			return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getCenter());
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

}
