package model.board;

import image_library.Tile;
import model.keys.NodeKey;
import view.ViewInterface;

public class TreeNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	private ViewInterface view;
	
	TreeNode(NodeKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	///////////////////////// Accessor Methods //////////////////////
	
	public final Tile getTile(double[] coords, double[] dimensions) {
		return view.getTree(coords, dimensions);
	}
	
	//////////////////////// Checker Methods ///////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}


}
