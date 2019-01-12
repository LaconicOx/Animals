package model.board;
import model.ModelKey;
import view.ViewInterface;

public class RockNode extends Node{
	
	//Class Fields
	private final static boolean PASSABLE = false;
	private final static double WIND_FACTOR = 0.0;
	
	//Instance Fields
	
	public RockNode(ModelKey center, ViewInterface view){
		super(center, view.getRock(center.getCenter(), ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	@Override
	public final double  getWindFactor() {
		return WIND_FACTOR;
	}
	
	////////////////// Mutators /////////////////////////

	
	///////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

}
