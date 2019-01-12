package model.board.node_states;

import game.Directions.Direction;
import model.board.Node;

public class OnBase implements Active{
	
	private On context;
	
	public OnBase(On context) {
		this.context = context;
	}
	
	////////////////////////// Accessors ////////////////////////////
	
	@Override
	public final Node getNeighbor(Direction dir) {
		return context.getNeighbor(dir);
	}
	
	@Override
	public final double getScent() {
		return 0;
	}
	
	@Override
	public final double eat(double amount) {
		return context.changeFood(amount);
	}
	
	@Override
	public Active getWrapped() {
		return this;
	}
	
	////////////////////////// Mutators /////////////////////////////
	
	@Override
	public final void advance() {
		context.advance();
	}
	
	@Override
	public final void receiveScent(double scent) {
		context.changeScent(scent);
	}
	
	@Override
	public final void receiveWind(double[] wind) {
		context.changeWind(wind);
	}
	
	@Override
	public final void sendBorder() {
		//TODO Strip out of final
		context.sendBorder();
	}
	
	public final void setTileFacing(Direction facing) {
		context.setTileFacing(facing);
	}
	
	@Override
	public final Node shift(Direction toward) {
		System.err.println("Error in OnBase.shift");
		return null;
	}
	
	@Override
	public final void turnOff() {
		context.turnOff();
	}
	
	@Override
	public boolean update() {
		context.send();
		return false;
	}
	
	////////////////////// Checkers ////////////////////////////////
	
	@Override
	public boolean checkBorder() {
		return false;
	}
	
	public boolean checkDuplicates(boolean perivous) {
		//TODO Strip out of final
		return false;
	}
	
	@Override
	public final boolean checkInterior() {
		return true;
	}
	
	@Override
	public final boolean checkOff() {
		return false;
	}
	
	@Override
	public final boolean checkResting() {
		return context.checkResting();
	}

	/////////////////////////// Object Overrides ////////////////////////////
	
	

	

	

	

	
}
