package model.board;

import java.util.concurrent.ThreadLocalRandom;

import image_library.Tile;
import model.ModelKey;
import view.ViewInterface;

public class BushNode extends Node{
	
	//Class Fields and Constants
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 5;
	private final static double WIND_FACTOR = 0.75;
	private final static double FOOD_GROWTH = 0.005;
	private final static double FOOD_MAX = 10.0;
	
	//Instance Fields
	private double food;
	private final ViewInterface view;
	
	BushNode(ModelKey center, ViewInterface view){
		super(center);
		this.view = view;
		food = FOOD_MAX;
	}
	
	//////////////////// Accessor Methods ///////////////////////
	
	@Override
	public final Tile getTile() {
		return view.getBush(getCenter(), ModelKey.getDimensions());
	}
	
	@Override
	protected final double getWindFactor() {
		return WIND_FACTOR;
	}
	
	//////////////////// Mutator////////////////////////////
	
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
	
	/////////////////// Checker Methods //////////////////////
	
	@Override
	public boolean checkPassable() {return PASSABLE;}

	@Override
	public boolean checkDeer() {
		int outcome = ThreadLocalRandom.current().nextInt(1, 101);
		if(outcome <= DEER_ODDS	)
			return true;
		else
			return false;
	}
}
