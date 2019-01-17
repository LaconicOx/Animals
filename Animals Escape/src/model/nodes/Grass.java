
package model.nodes;


import java.util.concurrent.ThreadLocalRandom;

import view.ViewInterface;

class Grass extends ConcreteNode{
	
	//Class Fields
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 10;
	private final static double WIND_FACTOR = 1.0;
	private final static double FOOD = 20.0;
	
	Grass(ModelKey center, ViewInterface view){
		super(center, view.getGrass(center.getCenter(),ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	/////////////////////// Accessor Methods //////////////////////
	
	@Override
	final double getFoodFactor() {
		return FOOD;
	}
	
	@Override
	final double getWindFactor() {
		return WIND_FACTOR;
	}

	
	///////////////////// Checker Methods /////////////////////////
	
	@Override
	public final boolean checkPassable() {
		return PASSABLE;
		}

	@Override
	boolean checkDeer() {
		int roll = ThreadLocalRandom.current().nextInt(1, 101);
		if (roll <= DEER_ODDS)
			return true;
		else
			return false;
	}
	
	////////////////////////// Overrides ////////////////////////
	@Override
	public String toString() {
		return "Grass at " + super.toString();
	}
}
