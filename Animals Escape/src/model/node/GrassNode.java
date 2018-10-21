package model.node;


import commands.swing.CommandFactory;

public class GrassNode extends Node{
	
	private final boolean passable = true;
	
	GrassNode(NodeKey center){
		super(center);
	}
	
	public void getCommand(double[] center) {
		CommandFactory.getDrawGrass(center);
	}
	
	
	@Override
	public boolean isPassable() {return passable;}

	
}
