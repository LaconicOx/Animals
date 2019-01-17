package model.nodes;

import view.ViewInterface;

class Tree extends ConcreteNode{
	
	//Class Fields
	private final static boolean PASSABLE = false;
	private final static double WIND_FACTOR = 0.1;
	private final static double FOOD = 0.0;
	
	//Instance Fields
	
	Tree(ModelKey center, ViewInterface view){
		super(center, view.getTree(center.getCenter(), ModelKey.getDimensions()), view.getBorder(center.getCenter(), ModelKey.getDimensions()));
	}
	
	///////////////////////// Accessor Methods //////////////////////
	
	@Override
	final double getWindFactor() {
		return WIND_FACTOR;
	}
	
	@Override
	final double getFoodFactor() {
		return FOOD;
	}
	//////////////////////// Mutators //////////////////////////
	
	
	//////////////////////// Checker Methods ///////////////////////
	
	@Override
	boolean checkDeer() {
		return false;
	}
	
	@Override
	public boolean checkPassable() {return PASSABLE;}


}
