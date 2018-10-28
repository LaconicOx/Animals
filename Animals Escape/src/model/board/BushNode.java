package model.board;


import commands.swing.CommandFactory;

public class BushNode extends Node{
	
	private final boolean passable = true;

	
	BushNode(NodeKey center){
		super(center);
	}
	
	public void getCommand(double[] vertices) {
		CommandFactory.getDrawBush(vertices);
	}
	
	@Override
	public boolean isPassable() {return passable;}


}
