package model.nodes;

import game.Directions.Direction;

abstract class OnWrapper extends OnState{
	
	protected OnState active;
	
	protected OnWrapper(OnState state) {
		this.active = state;
	}
	
	//////////////////////// Accessors ////////////////////////
	
	@Override
	final Node getNeighbor(Direction dir) {
		return active.getNeighbor(dir);
	}
	
	@Override
	double getScent() {
		return active.getScent();
	}
	
	@Override
	final OnState getWrapped() {
		return active;
	}
	
	/////////////////////// Mutators ///////////////////////////
	
	@Override
	final void advance() {
		active.advance();
	}
	
	@Override
	double eat(double amount) {
		return 0.0;
	}
	
	@Override
	final void setScent(double scent) {
		active.setScent(scent);
	}
	
	@Override
	void receiveWind(double[] wind) {
		active.receiveWind(wind);
	}
	
	@Override
	final void setTileFacing(Direction facing) {
		active.setTileFacing(facing);
	}
	
	//////////////////////// Checkers ////////////////////////
	
	@Override
	final boolean checkResting() {
		return active.checkResting();
	}
	
}
