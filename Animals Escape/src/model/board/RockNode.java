package model.board;

import commands.swing.CommandFactory;
import units.CellKey;
import units.NodeKey;

public class RockNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	public RockNode(NodeKey center){
		super(center);
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	public void getCommand(CellKey key) {
		CommandFactory.getDrawRock(key);
	}
	
	///////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	
}
