package model.nodes;

import game.Directions.Direction;

abstract class NodeState {
	
	///////////////////////// Accessors /////////////////////////////
	
	abstract double eat(double amount);
	abstract protected double getFoodFactor();
	protected abstract Node getNeighbor(Direction dir);
	abstract Pattern getPattern();
	abstract double getScent();
	abstract protected double getWindFactor();
	abstract NodeState getWrapped();
	
	//////////////////////// Mutators ///////////////////////////////
	
	
	abstract protected void advance();
	abstract protected void sendBorder();
	abstract protected void sendTile();
	abstract void setScent(double scent);
	abstract protected void setTileFacing(Direction facing);
	
	/**
	 * Recieves vector representing wind a wind value and stores it in a list.
	 * @param wind - vector representing a wind value.
	 */
	abstract void setWind(double[] wind);
	
	/**
	 * Performs update according to state.
	 * @return True if wrapper should be stripped; false otherwise.
	 */
	abstract boolean update();
	
	////////////////////// Checkers //////////////////////////////
	
	abstract boolean checkBorder();
	abstract boolean checkBorder(Direction dir);
	abstract boolean checkInterior();
	abstract boolean checkInterior(Direction dir);
	abstract boolean checkOff();
	abstract boolean checkOff(Direction dir);
	abstract protected boolean checkResting();
	
	////////////////////// Object Overrides ////////////////////////
	
	@Override
	abstract public String toString();
	
}
