package model.nodes;

import game.Directions.Direction;

abstract class OnState {
	
	abstract double eat(double amount);
	abstract Node getNeighbor(Direction dir);
	abstract double getScent();
	abstract OnState getWrapped();
	
	abstract void advance();
	abstract void setScent(double scent);
	abstract void receiveWind(double[] wind);
	abstract void setTileFacing(Direction facing);
	abstract boolean update();
	
	abstract boolean checkResting();
}
