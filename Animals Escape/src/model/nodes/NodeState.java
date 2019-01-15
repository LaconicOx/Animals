package model.nodes;

import game.Directions.Direction;

abstract class NodeState {
	
	///////////////////////// Accessors /////////////////////////////
	
	abstract double eat(double amount);
	abstract double getScent();
	protected abstract Node getNeighbor(Direction dir);
	abstract protected double getFoodFactor();
	abstract protected double getWindFactor();
	
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
	
	abstract boolean checkInterior();
	abstract boolean checkBorder();
	abstract boolean checkOff();
	abstract protected boolean checkResting();
	
	////////////////////// Object Overrides ////////////////////////
	
	@Override
	abstract public String toString();
	
}
