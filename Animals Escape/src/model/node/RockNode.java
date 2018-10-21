package model.node;

import commands.swing.CommandFactory;

public class RockNode extends Node{
	
	private final boolean passable = false;
	
	RockNode(NodeKey center){
		super(center);
	}
	
	public void getCommand(double[] vertices) {
		CommandFactory.getDrawRock(vertices);
	}
	
	
	@Override
	public boolean isPassable() {return passable;}

	
}
