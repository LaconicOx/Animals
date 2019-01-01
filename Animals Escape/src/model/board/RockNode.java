package model.board;

import image_library.Tile;
import model.ModelKey;
import view.ViewInterface;

public class RockNode extends Node{
	
	//Class Fields
	private final static boolean PASSABLE = false;
	private final static double WIND_FACTOR = 0.0;
	
	//Instance Fields
	private ViewInterface view;
	
	public RockNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	@Override
	public final Tile getTile() {
		return view.getRock(getCenter(), ModelKey.getDimensions());
	}
	
	@Override
	protected final double  getWindFactor() {
		return WIND_FACTOR;
	}
	
	///////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	
}
