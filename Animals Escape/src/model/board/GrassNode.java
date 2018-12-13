
package model.board;


import image_library.Tile;
import model.keys.NodeKey;
import view.ViewInterface;

public class GrassNode extends Node{
	
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 10;
	
	private ViewInterface view;
	
	public GrassNode(NodeKey center, ViewInterface view){
		super(center);
		this.view = view;
	}
	
	/////////////////////// Accessor Methods //////////////////////
	
	@Override
	public Tile getTile(double[] coords, double[] dimensions) {
		return view.getGrass(coords, dimensions);
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
