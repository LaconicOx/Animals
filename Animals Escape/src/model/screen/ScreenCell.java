package model.screen;


import java.util.Objects;

import image_library.Tile;
import model.board.Node;

public class ScreenCell extends Cell {
	
	private final Tile tile;
	
	/////////////////////// Constructor and Initializers ////////////////////////////
	public ScreenCell(Node node){
		super(node);
		tile = node.getTile();
		//  initCharacters(); 
	}
	
	/////////////////////////// Mutator Methods ////////////////////////////
	
	@Override
	public void update() {
		tile.advance();
		tile.send();
	}
	
	///////////////////////////// Overrides ///////////////////////////////
	
	@Override
	public String toString() {
		double[] center = getCenter();
		return "ScreenCell (" + center[0] + "," + center[1] + ") maps to Node " + super.toString();
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

}
