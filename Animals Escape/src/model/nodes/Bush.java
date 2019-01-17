package model.nodes;

import java.util.concurrent.ThreadLocalRandom;

import view.ViewInterface;

class Bush extends ConcreteNode{
	
	//Class Fields and Constants
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 5;
	private final static double WIND_FACTOR = 0.75;
	private final static double FOOD = 10.0;
	
	Bush(ModelKey center, ViewInterface view){
		super(center, view.getBush(center.getCenter(), ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	//////////////////// Accessor Methods ///////////////////////
	
	@Override
	final double getFoodFactor() {
		return FOOD;
	}
	
	@Override
	final double getWindFactor() {
		return WIND_FACTOR;
	}
	
	/////////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	@Override
	boolean checkDeer() {
		int outcome = ThreadLocalRandom.current().nextInt(1, 101);
		if(outcome <= DEER_ODDS	)
			return true;
		else
			return false;
	}
	
}
