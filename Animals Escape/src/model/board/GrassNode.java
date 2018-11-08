
package model.board;


import commands.swing.CommandFactory;

public class GrassNode extends Node{
	
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 10;
	
	public GrassNode(NodeKey center){
		super(center);
	}
	
	/////////////////////// Accessor Methods //////////////////////
	
	@Override
	public void getCommand(double[] center) {
		CommandFactory.getDrawGrass(center);
	}
	
	///////////////////// Checker Methods /////////////////////////
	@Override
	public boolean isPassable() {return PASSABLE;}

	@Override
	public boolean checkDeer() {
		int roll = getRanInt();
		if (roll <= DEER_ODDS)
			return true;
		else
			return false;
	}
}
