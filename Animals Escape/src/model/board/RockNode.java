package model.board;

import commands.swing.CommandFactory;

public class RockNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	public RockNode(NodeKey center){
		super(center);
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	public void getCommand(double[] vertices) {
		CommandFactory.getDrawRock(vertices);
	}
	
	///////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean isPassable() {return PASSABLE;}

	
}
