package model.nodes;
import view.ViewInterface;

class Rock extends ConcreteNode{
	
	//Class Fields
	private final static boolean PASSABLE = false;
	private final static double WIND_FACTOR = 0.0;
	private final static double FOOD = 0.0;
	
	//Instance Fields
	
	Rock(ModelKey center, ViewInterface view){
		super(center, view.getRock(center.getCenter(), ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	/////////////////// Accessor Methods ////////////////////
	
	@Override
	final double  getWindFactor() {
		return WIND_FACTOR;
	}
	
	@Override
	final double getFoodFactor() {
		return FOOD;
	}
	////////////////// Mutators /////////////////////////

	
	///////////////// Checker Methods //////////////////////
	
	@Override
	boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

}
