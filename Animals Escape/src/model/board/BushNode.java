package model.board;

import image_library.Tile;
import model.keys.ModelKey;
import view.ViewInterface;

public class BushNode extends Node{
	
	//Class Fields and Constants
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 5;
	
	//Instance Fields
	private final ViewInterface view;
	
	BushNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	//////////////////// Accessor Methods ///////////////////////
	
	@Override
	public final Tile getTile() {
		return view.getBush(getCenter(), ModelKey.getDimensions());
	}
	
	/////////////////// Checker Methods //////////////////////
	@Override
	public boolean checkPassable() {return PASSABLE;}

	@Override
	public boolean checkDeer() {
		int outcome = getRanInt();
		if(outcome <= DEER_ODDS	)
			return true;
		else
			return false;
	}
}
