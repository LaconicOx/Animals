package model.nodes;

import game.Directions.Direction;

abstract class BaseWrapper extends NodeState{
	
	protected NodeState state;
	
	BaseWrapper(NodeState state){
		this.state = state;
	}
	
	
	///////////////////////// Accessors /////////////////////////////
	
	@Override
	double eat(double amount) {
		state.eat(amount);
		return 0;
	}
	
	@Override
	protected double getFoodFactor() {
		return state.getFoodFactor();
	}
	
	@Override
	protected Node getNeighbor(Direction dir) {
		return state.getNeighbor(dir);
	}
	
	@Override
	Pattern getPattern() {
		return state.getPattern();
	}
	
	@Override
	double getScent() {
		state.getScent();
		return 0;
	}
	
	@Override
	protected double getWindFactor() {
		return state.getWindFactor();
	}
	
	@Override
	NodeState getWrapped() {
		return state;
	}
	
	/////////////////////// Mutators ///////////////////////
	
	@Override
	protected void advance() {
		state.advance();
	}
	
	@Override
	protected void sendBorder() {
		state.sendBorder();
	}
	
	@Override
	void setScent(double scent) {
		state.setScent(scent);
		
	}
	
	@Override
	void setWind(double[] wind) {
		state.setWind(wind);
		
	}
	
	@Override
	protected void sendTile() {
		state.sendTile();
	}
	
	@Override
	protected void setTileFacing(Direction facing) {
		state.setTileFacing(facing);
	}
	
	//////////////////////Checkers //////////////////////////////
	
	@Override
	final boolean checkBase() {
		return false;
	}
	
	boolean checkBorder() {
		return false;
	}
	
	@Override
	boolean checkBorder(Direction dir) {
		return state.checkBorder(dir);
	}
	
	boolean checkInterior() {
		return false;
	}
	
	@Override
	boolean checkInterior(Direction dir) {
		return state.checkInterior(dir);
	}
	
	boolean checkOff() {
		return false;
	}
	
	@Override
	boolean checkOff(Direction dir) {
		return state.checkOff(dir);
	}
	
	@Override
	final protected boolean checkResting() {
		return state.checkResting();
	}
	
	
}
