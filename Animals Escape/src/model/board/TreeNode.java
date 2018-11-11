package model.board;

import commands.swing.CommandFactory;
import units.CellKey;
import units.NodeKey;

public class TreeNode extends Node{
	
	private final static boolean PASSABLE = false;
	
	TreeNode(NodeKey nk){
		super(nk);
	}
	
	///////////////////////// Accessor Methods //////////////////////
	
	public void getCommand(CellKey key) {
		CommandFactory.getDrawTree(key);
	}
	
	//////////////////////// Checker Methods ///////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}


}
