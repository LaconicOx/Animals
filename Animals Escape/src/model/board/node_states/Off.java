package model.board.node_states;

import game.Directions.Direction;
import model.board.Node;

public class Off implements NodeState{
	
	private final Node node;
	
	public Off(Node node) {
		this.node = node;
	}
	
	//////////////////// Accessors ////////////////////////
	
	@Override
	public double eat(double amount) {
		return 0;
	}
	
	@Override
	public final double getScent() {
		return 0;
	}
	
	/////////////////////// Mutators ///////////////////////
	
	@Override
	public void receiveScent(double scent) {
		// Do nothing
		
	}

	@Override
	public void receiveWind(double[] wind) {
		// Do nothing
	}

	@Override
	public Node shift(Direction toward) {
		System.err.println("Error in Off.shift");
		System.exit(0);
		return null;
	}
	
	@Override
	public final void update() {
		node.resetOn();
		
	}
	
	//////////////////////// Checkers //////////////////////////
	
	@Override
	public final boolean checkInterior() {
		return false;
	}

	@Override
	public final boolean checkBorder() {
		return false;
	}
	
	@Override
	public boolean checkOff() {
		return true;
	}
	
	////////////////////// Object Overrides ///////////////////////
	
	@Override
	public String toString() {
		return " is Off";
	}

	

}
