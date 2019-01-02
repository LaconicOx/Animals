package model.screen;


import java.util.Objects;

import game.Directions.Direction;
import image_library.Tile;
import model.board.Node;

public class ScreenCell extends Cell {
	
	//Class Fields
	private static final double[] ORIGIN = {0,0};
	
	//Instance Fields
	private final Tile tile;
	private Direction facing;
	
	/////////////////////// Constructor and Initializers ////////////////////////////
	public ScreenCell(Node node){
		super(node);
		tile = node.getTile();
		facing = Direction.N;
	}
	
	/////////////////////////// Mutator Methods ////////////////////////////
	
	public double eatFood(double quantity) {
		return node.eatFood(quantity);
	}
	
	public void setScent(double quantity) {
		node.setScent(quantity);
	}
	
	@Override
	public void update() {
		
		//Node updating
		node.updateFood();
		node.updateScent();
		
		//Wind updating
		double[] windDir = node.getWind();
		if (windDir[0] != 0 || windDir[1] !=0) {
			//System.out.println("Blowing");
			facing = Direction.getDirection(ORIGIN, windDir);
			tile.advance(facing);
		}
		else {
			//Advances the tile if not at resting position.
			if(!tile.checkResting())
				tile.advance(facing);
		}
		
		//Primes animation
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
