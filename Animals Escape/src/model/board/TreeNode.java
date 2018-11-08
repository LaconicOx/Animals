package model.board;

import commands.swing.CommandFactory;

public class TreeNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	TreeNode(NodeKey nk){
		super(nk);
	}
	
	///////////////////////// Accessor Methods //////////////////////
	
	public void getCommand(double[] center) {
		CommandFactory.getDrawTree(center);
	}
	
	//////////////////////// Checker Methods ///////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean isPassable() {return PASSABLE;}


}
