package model.board.node_states;

import game.Directions.Direction;
import model.board.Node;

public abstract class OnStates implements Active{
	
	protected Active active;
	
	protected OnStates(Active active) {
		this.active = active;
	}
	
	//////////////////////// Accessors ////////////////////////
	
	@Override
	public final Node getNeighbor(Direction dir) {
		return active.getNeighbor(dir);
	}
	
	@Override
	public double getScent() {
		return active.getScent();
	}
	
	@Override
	public final Active getWrapped() {
		return active;
	}
	
	/////////////////////// Mutators ///////////////////////////
	
	@Override
	public final void advance() {
		active.advance();
	}
	
	@Override
	public double eat(double amount) {
		return 0.0;
	}
	
	@Override
	public void receiveScent(double scent) {
		active.receiveScent(scent);
	}
	
	@Override
	public void receiveWind(double[] wind) {
		active.receiveWind(wind);
	}
	
	public final void sendBorder() {
		active.sendBorder();
	}
	
	@Override
	public final void setTileFacing(Direction facing) {
		active.setTileFacing(facing);
	}
	
	@Override
	public Node shift(Direction toward) {
		return active.shift(toward);
	}
	
	@Override
	public final void turnOff() {
		active.turnOff();
	}
	
	//////////////////////// Checkers ////////////////////////
	
	@Override
	public boolean checkDuplicates(boolean previous) {
		//TODO Strip out of final
		return active.checkDuplicates(previous);
	}
	
	@Override
	public boolean checkInterior() {
		return active.checkInterior();
	}
	
	@Override
	public boolean checkBorder() {
		return active.checkBorder();
	}
	
	@Override
	public final boolean checkOff() {
		return active.checkOff();
	}
	
	@Override
	public final boolean checkResting() {
		return active.checkResting();
	}
	
}
