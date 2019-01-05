package model.board.node_states;

import game.Directions.Direction;

public interface NodeState {
	
	public void shift(Direction toward);//Returns true if cell should be removed from Screen.
	
	
}
