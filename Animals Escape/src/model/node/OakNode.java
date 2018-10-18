package model.node;

import commands.swing.CommandFactory;

public class OakNode extends Node{
	
	private final boolean passable = false;
	
	OakNode(NodeKey nk){
		super(nk);
	}
	
	public void getCommand(double[][] vertices) {
		CommandFactory.getDrawOak(vertices);
	}
	
	public void getCommand(double[] center) {}
	
	@Override
	public boolean isPassable() {return passable;}


}
