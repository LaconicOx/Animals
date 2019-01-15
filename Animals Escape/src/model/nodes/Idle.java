package model.nodes;

import game.Directions.Direction;

class Idle extends OnState{
	
	private On context;
	
	Idle(On context) {
		this.context = context;
	}
	
	////////////////////////// Accessors ////////////////////////////
	
	@Override
	final Node getNeighbor(Direction dir) {
		return context.getNeighbor(dir);
	}
	
	@Override
	final double getScent() {
		return 0;
	}
	
	@Override
	final double eat(double amount) {
		return context.wrapFood(amount);
	}
	
	@Override
	OnState getWrapped() {
		return this;
	}
	
	////////////////////////// Mutators /////////////////////////////
	
	@Override
	final void advance() {
		context.advance();
	}
	
	@Override
	final void setScent(double scent) {
		context.wrapScent(scent);
	}
	
	@Override
	final void receiveWind(double[] wind) {
		context.wrapWind(wind);
	}
	
	final void setTileFacing(Direction facing) {
		context.setTileFacing(facing);
	}
	
	@Override
	boolean update() {
		context.send();
		return false;
	}
	
	////////////////////// Checkers ////////////////////////////////
	
	@Override
	final boolean checkResting() {
		return context.checkResting();
	}

	/////////////////////////// Object Overrides ////////////////////////////
	
	

	

	

	

	
}
