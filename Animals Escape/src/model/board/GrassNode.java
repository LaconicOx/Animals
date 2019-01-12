
package model.board;


import java.util.concurrent.ThreadLocalRandom;

import model.ModelKey;
import view.ViewInterface;

public class GrassNode extends Node{
	
	//Class Fields
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 10;
	private final static double WIND_FACTOR = 1.0;
	private final static double FOOD = 20.0;
	
	public GrassNode(ModelKey center, ViewInterface view){
		super(center, view.getGrass(center.getCenter(),ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	/////////////////////// Accessor Methods //////////////////////
	
	@Override
	public final double getFood() {
		return FOOD;
	}
	
	@Override
	public final double getWindFactor() {
		return WIND_FACTOR;
	}
	
	////////////////////Mutator////////////////////////////

	
	///////////////////// Checker Methods /////////////////////////
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	@Override
	public boolean checkDeer() {
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
