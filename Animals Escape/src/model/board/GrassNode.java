
package model.board;


import java.util.concurrent.ThreadLocalRandom;

import image_library.Tile;
import model.ModelKey;
import view.ViewInterface;

public class GrassNode extends Node{
	
	//Class Fields
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 10;
	private final static double WIND_FACTOR = 1.0;
	private final static double FOOD_GROWTH = 0.01;
	private final static double FOOD_MAX = 20.0;
	
	//Instance Fields
	private ViewInterface view;
	private double food;
	
	public GrassNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
		food = FOOD_MAX;
	}
	
	/////////////////////// Accessor Methods //////////////////////
	
	@Override
	public Tile getTile() {
		return view.getGrass(getCenter(), ModelKey.getDimensions());
	}
	
	@Override
	protected final double getWindFactor() {
		return WIND_FACTOR;
	}
	
	////////////////////Mutator////////////////////////////
	
	@Override
	public final void updateFood() {
		if (food < FOOD_MAX) {
			food += FOOD_GROWTH;
		}
	}
	
	@Override
	public double eatFood(double quantity) {
		food -= quantity;
		if (food > 0.0)
			return quantity;
		else {
			food = 0.0;
			return 0.0;
		}
	}
	
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
}
