
package model.board;


import image_library.Tile;
import model.ModelKey;
import view.ViewInterface;

public class GrassNode extends Node{
	
	//Class Fields
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 10;
	private final static double WIND_FACTOR = 1.0;
	
	//Instance Fields
	private ViewInterface view;
	
	public GrassNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	/////////////////////// Accessor Methods //////////////////////
	
	@Override
	public Tile getTile() {
		return view.getGrass(getCenter(), ModelKey.getDimensions());
	}
	
	@Override
	protected final double getWindFactor() {
		return WIND_FACTOR;
	}
	
	///////////////////// Checker Methods /////////////////////////
	@Override
	public boolean checkPassable() {return PASSABLE;}

	@Override
	public boolean checkDeer() {
		int roll = getRanInt();
		if (roll <= DEER_ODDS)
			return true;
		else
			return false;
	}
}
