package model.board;

import java.util.concurrent.ThreadLocalRandom;

import model.ModelKey;
import view.ViewInterface;

public class BushNode extends Node{
	
	//Class Fields and Constants
	private final static boolean PASSABLE = true;
	private final static int DEER_ODDS = 5;
	private final static double WIND_FACTOR = 0.75;
	
	private final static double FOOD = 10.0;
	
	BushNode(ModelKey center, ViewInterface view){
		super(center, view.getBush(center.getCenter(), ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	//////////////////// Accessor Methods ///////////////////////
	
	@Override
	public final double getFood() {
		return FOOD;
	}
	
	@Override
	public final double getWindFactor() {
		return WIND_FACTOR;
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
