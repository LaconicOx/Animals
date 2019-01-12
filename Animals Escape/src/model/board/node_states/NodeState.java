package model.board.node_states;

import game.Directions.Direction;
import model.board.Node;

public interface NodeState {
	
	///////////////////////// Accessors /////////////////////////////
	
	public double getScent();
	
	//////////////////////// Mutators ///////////////////////////////
	
	public double eat(double amount);
	
	public void receiveScent(double scent);
	
	/**
	 * Recieves vector representing wind a wind value and stores it in a list.
	 * @param wind - vector representing a wind value.
	 */
	public void receiveWind(double[] wind);
	
	public Node shift(Direction toward);
	
	/**
	 * Performs update according to state.
	 * @return True if wrapper should be stripped; false otherwise.
	 */
	public void update();
	
	////////////////////// Checkers //////////////////////////////
	
	public boolean checkInterior();
	public boolean checkBorder();
	public boolean checkOff();
	
	////////////////////// Object Overrides ////////////////////////
	
	@Override
	public String toString();
	
}
