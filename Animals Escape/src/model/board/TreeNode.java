package model.board;

import image_library.Tile;
import model.keys.ModelKey;
import view.ViewInterface;

public class TreeNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	private ViewInterface view;
	
	TreeNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	///////////////////////// Accessor Methods //////////////////////
	
	public final Tile getTile() {
		return view.getTree(getCenter(), ModelKey.getDimensions());
	}
	
	//////////////////////// Checker Methods ///////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}


}
