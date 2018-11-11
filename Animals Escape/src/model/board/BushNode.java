package model.board;

import commands.swing.CommandFactory;
import units.CellKey;
import units.NodeKey;

public class BushNode extends Node{
	
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 5;
	
	BushNode(NodeKey center){
		super(center);
	}
	
	//////////////////// Accessor Methods ///////////////////////
	
	@Override
	public void getCommand(CellKey key) {
		CommandFactory.getDrawBush(key);
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
