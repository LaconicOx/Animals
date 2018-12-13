package model.board;

import image_library.Tile;
import model.keys.NodeKey;
import view.ViewInterface;

public class RockNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	private ViewInterface view;
	
	public RockNode(NodeKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	public Tile getTile(double[] coords, double[] dimensions) {
		return view.getRock(coords, dimensions);
	}
	
	///////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	
}
