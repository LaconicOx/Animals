package model.board;

import commands.swing.CommandFactory;

public class TreeNode extends Node{
	
	private final boolean passable = false;
	
	TreeNode(NodeKey nk){
		super(nk);
	}
	
	public void getCommand(double[] center) {
		CommandFactory.getDrawTree(center);
	}
	
	
	@Override
	public boolean isPassable() {return passable;}


}
