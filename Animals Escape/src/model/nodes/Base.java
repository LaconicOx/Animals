package model.nodes;

import game.Directions.Direction;

class Base extends NodeState{
	
	private final ConcreteNode node;
	
	Base(ConcreteNode node) {
		this.node = node;
	}
	
	///////////////////////// Accessors /////////////////////////////
	
	@Override
	final double eat(double amount) {
		System.err.println("Base.eat called");
		System.exit(0);
		return 0;
	}
	
	final protected double getFoodFactor() {
		return node.getFoodFactor();
	}
	
	@Override
	final protected Node getNeighbor(Direction dir) {
		return node.getNeighbor(dir);
	}
	
	@Override
	final Pattern getPattern() {
		System.err.println("Base.getPattern called");
		System.exit(0);
		return null;
	}
	
	@Override
	final double getScent() {
		System.err.println("Base.getScent called");
		System.exit(0);
		return 0;
	}
	
	@Override
	final protected double getWindFactor() {
		return node.getWindFactor();
	}
	
	@Override
	final NodeState getWrapped() {
		return this;
	}
	
	////////////////////////Mutators ///////////////////////////////
	
	@Override
	final protected void advance() {
		node.advance();
	}
	
	@Override
	final protected void sendBorder() {
		node.sendBorder();
	}
	
	@Override
	final protected void sendTile() {
		node.sendTile();
	}
	
	@Override
	final void setScent(double scent) {
		System.err.println("Base.setScent called");
		System.exit(0);
		
	}
	
	@Override
	final protected void setTileFacing(Direction facing) {
		node.setTileFacing(facing);
	}
	
	@Override
	final void setWind(double[] wind) {
		System.err.println("Base.setWind called");
		System.exit(0);
		
	}

	@Override
	final boolean update() {
		System.err.println("Base.update called");
		System.exit(0);
		
		return false;
	}
	
	//////////////////////Checkers //////////////////////////////
	
	@Override
	final boolean checkBase() {
		return true;
	}
	
	@Override
	final boolean checkBorder() {
		System.err.println("Base.checkBorder called");
		System.exit(0);
		return false;
	}
	
	@Override
	final boolean checkBorder(Direction dir) {
		return node.getNeighbor(dir).checkBorder();
	}
	
	@Override
	final boolean checkInterior(Direction dir) {
		return node.getNeighbor(dir).checkInterior();
	}
	
	@Override
	final boolean checkInterior() {
		System.err.println("Base.checkInterior called for " + node);
		System.exit(0);
		return false;
	}
	
	@Override
	final boolean checkOff() {
		System.err.println("Base.checkOff called");
		System.exit(0);
		return false;
	}
	
	@Override
	boolean checkOff(Direction dir) {
		return node.getNeighbor(dir).checkOff();
	}
	
	@Override
	final protected boolean checkResting() {
		return node.checkResting();
	}
	
	////////////////////// Object Overrides ////////////////////////
	
	@Override
	final public String toString() {
		return " with base";
	}

}
