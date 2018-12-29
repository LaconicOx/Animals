package model.board;

import image_library.Tile;
import model.keys.ModelKey;
import view.ViewInterface;

public class RockNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	private ViewInterface view;
	
	public RockNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	public Tile getTile() {
		return view.getRock(getCenter(), ModelKey.getDimensions());
	}
	
	///////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	
}
