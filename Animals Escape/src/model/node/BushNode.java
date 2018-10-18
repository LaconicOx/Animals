package model.node;


import commands.swing.CommandFactory;

public class BushNode extends Node{
	
	private final boolean passable = true;

	
	BushNode(NodeKey center){
		super(center);
	}
	
	public void getCommand(double[][] vertices) {
		CommandFactory.getDrawBush(vertices);
	}
	
	public void getCommand(double[] center) {}
	
	@Override
	public boolean isPassable() {return passable;}


}
